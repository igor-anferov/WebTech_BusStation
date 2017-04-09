package bus_station.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "Stop")
@Table(name = "Stops")
public class Stop {
    @Id
    @Column(name = "Stop_")
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Integer id;
    @ManyToOne
    @JoinColumn(name="Run")
    private Run run;
    @ManyToOne
    @JoinColumn(name="Station")
    private Station station;
    @Temporal(TemporalType.TIMESTAMP)
    private Date arrival;
    @Temporal(TemporalType.TIMESTAMP)
    private Date departure;
    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Part> partsFrom = new ArrayList<>();
    @OneToMany(mappedBy = "to", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Part> partsTo = new ArrayList<>();
    
    public Stop() {
    }

    public Stop(Run run, Station station, Date arrival, Date departure) {
        this.run = run;
        this.station = station;
        this.arrival = arrival;
        this.departure = departure;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Run getRun() {
        return run;
    }

    public void setRun(Run run) {
        this.run = run;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Date getArrival() {
        return arrival;
    }

    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public List<Part> getPartsFrom() {
        return partsFrom;
    }

    public List<Part> getPartsTo() {
        return partsTo;
    }

    public void addPartFrom(Part part) {
        partsFrom.add( part );
        part.setFrom( this );
    }

    public void removePartFrom(Part part) {
        partsFrom.remove( part );
        part.setFrom( null );
    }

    public void addPartTo(Part part) {
        partsTo.add( part );
        part.setTo( this );
    }

    public void removePartTo(Part part) {
        partsTo.remove( part );
        part.setTo( null );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stop stop = (Stop) o;

        if (run != null ? !run.equals(stop.run) : stop.run != null) return false;
        if (station != null ? !station.equals(stop.station) : stop.station != null) return false;
        if (arrival != null ? !arrival.equals(stop.arrival) : stop.arrival != null) return false;
        return departure != null ? departure.equals(stop.departure) : stop.departure == null;
    }

    @Override
    public int hashCode() {
        int result = run != null ? run.hashCode() : 0;
        result = 31 * result + (station != null ? station.hashCode() : 0);
        result = 31 * result + (arrival != null ? arrival.hashCode() : 0);
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Stop{" +
                "id=" + id +
                ", run=" + run +
                ", station=" + station +
                ", arrival=" + arrival +
                ", departure=" + departure +
                '}';
    }
}
