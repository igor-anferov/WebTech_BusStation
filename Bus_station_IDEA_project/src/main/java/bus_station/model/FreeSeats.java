package bus_station.model;

import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "FindRunView")
@Subselect( "SELECT Parts.Part, BusCapacity-SUM(IFNULL(Count, 0)) as Free\n" +
            "FROM Runs JOIN\n" +
            "     Stops as f ON Runs.Run = f.Run JOIN\n" +
            "     Stops as t ON Runs.Run = t.Run JOIN\n" +
            "     Parts ON From_ = f.Stop_ AND To_ = t.Stop_ JOIN\n" +
            "     Stops as fc ON fc.Run = Runs.Run JOIN\n" +
            "     Stops as tc ON fc.Run = Runs.Run AND NOT (tc.Arrival <= f.Departure OR fc.Departure >= t.Arrival) JOIN\n" +
            "     Parts as pc ON pc.From_ = fc.Stop_ AND pc.To_ = tc.Stop_ LEFT JOIN\n" +
            "     Orders ON Orders.Part = pc.Part\n" +
            "GROUP BY Part")
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
}
