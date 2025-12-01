package com.app.cert;

import com.app.category.Category;
import com.app.enums.CertReason;
import com.app.ordering.Ordering;
import com.app.util.Global;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cert implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cert_g")
    @SequenceGenerator(name = "cert_g", sequenceName = "cert_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String address;
    private float price;
    private int term;
    private int views = 0;

    @Column(length = 5000)
    private String description = "";

    @Column(length = 1000)
    private String img = "";
    @Column(length = 1000)
    private String file = "";

    @Enumerated(EnumType.STRING)
    private CertReason reason;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "cert", cascade = CascadeType.ALL)
    private List<Ordering> orderings = new ArrayList<>();

    public Cert(String name, String address, float price, int term, String description) {
        this.name = name;
        this.address = address;
        this.price = price;
        this.term = term;
        this.description = description;
    }

    public void update(Cert update) {
        this.name = update.getName();
        this.address = update.getAddress();
        this.price = update.getPrice();
        this.term = update.getTerm();
        this.description = update.getDescription();
    }

    public int getOrderingsSize() {
        return orderings.size();
    }

    public float getCtr() {
        if (views == 0 || getOrderingsSize() == 0) return 0;
        return Global.round((float) getOrderingsSize() / views);
    }

}