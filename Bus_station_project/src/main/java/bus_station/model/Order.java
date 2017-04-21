package bus_station.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "Order")
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "Order_")
    private Integer id;
    @ManyToOne
    @JoinColumn(name="Client")
    private Client client;
    @ManyToOne
    @JoinColumn(name="Part")
    private Part part;
    private Integer count;
    @Column(precision=8, scale=2)
    private BigDecimal price;

    public Order() {
    }

    public Order(Client client, Part part, Integer count) {
        this.client = client;
        this.part = part;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != null ? !id.equals(order.id) : order.id != null) return false;
        if (client != null ? !client.equals(order.client) : order.client != null) return false;
        if (part != null ? !part.equals(order.part) : order.part != null) return false;
        if (count != null ? !count.equals(order.count) : order.count != null) return false;
        return price != null ? price.equals(order.price) : order.price == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (part != null ? part.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client=" + client +
                ", part=" + part +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}
