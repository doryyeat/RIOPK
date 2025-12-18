package com.app.ordering;

import com.app.app_user.AppUser;
import com.app.cert.Cert;
import com.app.enums.OrderingStatus;
import com.app.enums.OrderingType;
import com.app.util.Global;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import static com.app.util.Global.getDateNow;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Ordering implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ordering_g")
    @SequenceGenerator(name = "ordering_g", sequenceName = "ordering_seq", allocationSize = 1)
    private Long id;

    private String dateStart = getDateNow();
    private String dateEnd;

    private int term;
    private String code;

    private String card;
    private float price;

    @Enumerated(EnumType.STRING)
    private OrderingType type;
    @Enumerated(EnumType.STRING)
    private OrderingStatus status = OrderingStatus.NOT_USED;

    @ManyToOne
    private Cert cert;
    @ManyToOne
    private AppUser owner;

    public Ordering(OrderingType type, Cert cert, AppUser owner) {
        this.type = type;
        this.cert = cert;
        this.owner = owner;

        this.term = cert.getTerm();

        LocalDate dateEnd = LocalDate.parse(this.dateStart, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        dateEnd = dateEnd.plusMonths(cert.getTerm());

        if (type == OrderingType.GIFT) {
            dateEnd = dateEnd.plusDays(10);
        }

        this.dateEnd = dateEnd.toString().substring(0, 10);

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            int digit = random.nextInt(10); // 0-9
            sb.append(digit);
        }

        this.code = sb.toString();
    }

    public String getDateStartFormat() {
        return Global.getDateFormatted(dateStart);
    }

    public String getDateEndFormat() {
        return Global.getDateFormatted(dateEnd);
    }

    public boolean isActivate() {
        LocalDate dateEnd = LocalDate.parse(this.dateEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate now = LocalDate.now();

        long daysBetween = ChronoUnit.DAYS.between(now, dateEnd);

        System.out.println("daysBetween: " + daysBetween);

        return daysBetween >= 0;
    }

}