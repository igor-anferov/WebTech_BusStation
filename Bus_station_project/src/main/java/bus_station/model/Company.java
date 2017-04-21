package bus_station.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Company")
@Table(name = "Companies")
public class Company {

    @Id
    @Column(name = "Company")
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Integer id;
    @Column(name = "CompanyName", unique = true, nullable = false)
    private String name;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Run> runs = new ArrayList<>();

    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Run> getRuns() {
        return runs;
    }

    public void addRun(Run run) {
        runs.add( run );
        run.setCompany( this );
    }

    public void removeRun(Run run) {
        runs.remove( run );
        run.setCompany( null );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        return name.equals(company.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
