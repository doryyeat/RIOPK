package com.app.ordering;

import com.app.app_user.AppUser;
import com.app.app_user.UserService;
import com.app.cert.CertService;
import com.app.enums.OrderingStatus;
import com.app.enums.OrderingType;
import com.app.system.exception.BadRequestException;
import com.app.system.exception.ObjectNotFoundException;
import com.app.util.Global;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
            case MANAGER -> orderings = repository.findAll();
            case USER -> orderings = user.getOrderings();
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

    public Ordering save(String type, String certId, String email) {
        OrderingType orderingType;

        try {
            orderingType = OrderingType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Некорректный выбор типа");
        }


        Ordering ordering = repository.save(new Ordering(orderingType, certService.find(certId), userService.getCurrentUser()));

        if (orderingType == OrderingType.GIFT) {
            Global.email_message(email, ordering);
        }

        return ordering;
    }

    public Ordering used(String id) {
        Ordering ordering = find(id);
        ordering.setStatus(OrderingStatus.USED);
        return repository.save(ordering);
    }

}
