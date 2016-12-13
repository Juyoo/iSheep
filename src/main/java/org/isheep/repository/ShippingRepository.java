package org.isheep.repository;

import org.isheep.entity.Customer;
import org.isheep.entity.Shipping;
import org.isheep.entity.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by raymo on 21/11/2016.
 *
 */
public interface ShippingRepository extends JpaRepository<Shipping, Long> {

    List<Shipping> findBySender(final Customer sender);
}
