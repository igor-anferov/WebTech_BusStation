package bus_station.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Part")
@Table(name = "Parts")
public class Part {
    @Id
    @Column(name = "Part")
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Integer id;
    @ManyToOne
    @JoinColumn(name="From_")
    private Stop from;
    @ManyToOne
    @JoinColumn(name="To_")
    private Stop to;
    @Column(precision=8, scale=2)
    private BigDecimal price;
    @OneToOne(mappedBy = "part", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private FreeSeats freeSeats;
    @OneToMany(mappedBy = "part", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public Part() {
    }

    public Part(Stop from, Stop to, BigDecimal price) {
        this.from = from;
        this.to = to;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Stop getFrom() {
        return from;
    }

    public void setFrom(Stop from) {
        this.from = from;
    }

    public Stop getTo() {
        return to;
    }

    public void setTo(Stop to) {
        this.to = to;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Integer getFreeSeats() {
        return freeSeats.getFree();
    }

    public void addOrder(Order order) {
        orders.add( order );
        order.setPart( this );
    }

    public void removeOrder(Order order) {
        orders.remove( order );
        order.setPart( null );
    }

    @Override
    public String toString() {
        return "Part{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", price=" + price +
                ", freeSeats=" + freeSeats.getFree() +
                '}';
    }
}
