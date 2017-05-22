package bus_station.model;

import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "FindRunView")
@Subselect( "SELECT LongPart.Part, BusCapacity - MAX(Taken) as Free\n" +
        "FROM Runs\n" +
        "     JOIN Parts as LongPart\n" +
        "        JOIN Stops as LongPartFromStop on LongPart.from_ = LongPartFromStop.Stop_\n" +
        "        JOIN Stops as LongPartToStop on LongPart.to_ = LongPartToStop.Stop_\n" +
        "                                    AND LongPartToStop.run = Runs.run\n" +
        "     JOIN Parts as ShortSubPart\n" +
        "         JOIN Stops as ShortSubPartFromStop on ShortSubPart.from_ = ShortSubPartFromStop.Stop_\n" +
        "        JOIN Stops as ShortSubPartToStop on ShortSubPart.to_ = ShortSubPartToStop.Stop_\n" +
        "                                        AND ShortSubPartToStop.run = Runs.run\n" +
        "            AND ShortSubPartFromStop.Departure >= LongPartFromStop.Departure\n" +
        "            AND ShortSubPartToStop.Arrival <= LongPartToStop.Arrival\n" +
        "            AND NOT EXISTS ( SELECT *\n" +
        "                             FROM Stops\n" +
        "                             WHERE Stops.run = Runs.run\n" +
        "                                    AND Stops.Arrival < ShortSubPartToStop.Arrival\n" +
        "                                    AND Stops.Departure > ShortSubPartFromStop.Departure\n" +
        "                           )\n" +
        "        LEFT JOIN (SELECT Parts.Part, SUM(IFNULL(Count, 0)) as Taken\n" +
        "                FROM Runs JOIN\n" +
        "                     Stops as f ON Runs.Run = f.Run JOIN\n" +
        "                     Stops as t ON Runs.Run = t.Run JOIN\n" +
        "                     Parts ON From_ = f.Stop_ AND To_ = t.Stop_ JOIN\n" +
        "                     Stops as fc ON fc.Run = Runs.Run JOIN\n" +
        "                     Stops as tc ON fc.Run = Runs.Run AND NOT (tc.Arrival <= f.Departure OR fc.Departure >= t.Arrival) JOIN\n" +
        "                     Parts as pc ON pc.From_ = fc.Stop_ AND pc.To_ = tc.Stop_ LEFT JOIN\n" +
        "                     Orders ON Orders.Part = pc.Part\n" +
        "                GROUP BY Part\n" +
        "             ) as Subselect ON Subselect.Part = ShortSubPart.Part\n" +
        "GROUP BY LongPart.Part")
@Synchronize({ "Parts", "Orders", "Stops" })
public class FreeSeats implements Serializable {
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Part")
    private Part part;
    @Column(name = "Free")
    private Integer free;

    public FreeSeats() {
    }

    public FreeSeats(Part part, Integer free) {
        this.part = part;
        this.free = free;
    }

    public Part getPart() {
        return part;
    }

    public Integer getFree() {
        return free;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FreeSeats freeSeats = (FreeSeats) o;

        return free.equals(freeSeats.free);
    }

    @Override
    public int hashCode() {
        return free.hashCode();
    }

    @Override
    public String toString() {
        return "FreeSeats{" +
                "free=" + free +
                '}';
    }
}
