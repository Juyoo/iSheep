package org.isheep.resource;

import org.isheep.entity.Shipping;
import org.isheep.entity.Tracking;
import org.isheep.repository.ShippingRepository;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Juyo on 07/12/2016.
 *
 */
@RequestMapping(TrackingResource.BASE_URL)
@RestController
public class TrackingResource {

    public static final String BASE_URL = "/tracking";
    private final ShippingRepository shippingRepository;

    @Inject
    public TrackingResource (final ShippingRepository shippingRepository){
        this.shippingRepository = shippingRepository;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/shipping-{shippingId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public final List<Tracking> trackShipping(@PathVariable("shippingId") final Long shippingId) {
        final Shipping shipping = shippingRepository.getOne(shippingId);

        final DateTime dateTimeNow = DateTime.now();

        final List<Tracking> trackings = shipping.getTrackings().stream()
                .filter(tracking -> !tracking.getEventDate().after(dateTimeNow.toDate()))
                .collect(Collectors.toList());

        shipping.setTrackings(trackings);

        return shipping.getTrackings();
    }

}
