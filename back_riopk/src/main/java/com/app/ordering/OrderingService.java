package com.app.ordering;

import com.app.app_user.AppUser;
import com.app.app_user.UserService;
import com.app.cert.Cert;
import com.app.cert.CertService;
import com.app.enums.OrderingStatus;
import com.app.enums.OrderingType;
import com.app.system.exception.BadRequestException;
import com.app.system.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.app.util.Global.email_message;
import static com.app.util.Global.round;

@Service
@RequiredArgsConstructor
public class OrderingService {

    private final OrderingRepository repository;
    private final CertService certService;
    private final UserService userService;

    public List<Ordering> findAll() {
        AppUser user = userService.getCurrentUser();

        List<Ordering> orderings = new ArrayList<>();

        switch (user.getRole()) {
            case MANAGER:
                for (Cert cert : user.getCerts()) {
                    orderings.addAll(cert.getOrderings());
                }
                break;
            case USER:
                orderings = user.getOrderings().stream().filter(ordering -> ordering.getType() == OrderingType.MYSELF).collect(Collectors.toList());
                break;
        }

        orderings.sort(Comparator.comparing(Ordering::getId).reversed());

        return orderings;
    }

    public List<Ordering> findAllByStatus(OrderingStatus status) {
        return repository.findAllByStatus(status);
    }

    public Ordering find(String id) {
        return repository.findById(Long.parseLong(id)).orElseThrow(() -> new ObjectNotFoundException("Не найден сертификат по ИД: " + id));
    }

    public Ordering save(String type, String certId, String card, float points, String email) {
        OrderingType orderingType;

        try {
            orderingType = OrderingType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Некорректный выбор типа");
        }

        Cert cert = certService.find(certId);
        AppUser user = userService.getCurrentUser();

        Ordering ordering = new Ordering(orderingType, cert, user);

        ordering.setCard(card);

        ordering.setPrice(cert.getPrice() - points);

        if (orderingType == OrderingType.GIFT) {
            email_message(email, ordering);
        }

        user.setPoints(user.getPoints() - points);
        user.setPoints(round(user.getPoints() + (ordering.getPrice() / 10)));

        userService.update(user);

        return repository.save(ordering);
    }

    public Ordering used(String id) {
        Ordering ordering = find(id);
        ordering.setStatus(OrderingStatus.USED);
        return repository.save(ordering);
    }

}
