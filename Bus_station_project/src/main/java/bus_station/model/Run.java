package bus_station.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Run")
@Table(name = "Runs")
public class Run {

    @Id
    @Column(name = "Run")
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Integer id;
    @Column(name = "RunNumber", unique = true, nullable = false)
    private String number;
    @ManyToOne
    @JoinColumn(name="Company")
    private Company company;
    private Integer busCapacity;
    @OneToMany(mappedBy = "run", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stop> stops = new ArrayList<>();

    public Run() {
    }

    public Run(String number, Company company, Integer busCapacity) {
        this.number = number;
        this.company = company;
        this.busCapacity = busCapacity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Integer getBusCapacity() {
        return busCapacity;
    }

    public void setBusCapacity(Integer busCapacity) {
        this.busCapacity = busCapacity;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void addStop(Stop stop) {
        stops.add( stop );
        stop.setRun( this );
    }

    public void removeStop(Stop stop) {
        stops.remove( stop );
        stop.setRun( null );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Run run = (Run) o;

        if (id != null ? !id.equals(run.id) : run.id != null) return false;
        if (number != null ? !number.equals(run.number) : run.number != null) return false;
        if (company != null ? !company.equals(run.company) : run.company != null) return false;
        return busCapacity != null ? busCapacity.equals(run.busCapacity) : run.busCapacity == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (busCapacity != null ? busCapacity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Run{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", company=" + company +
                ", busCapacity=" + busCapacity +
                '}';
    }
}
