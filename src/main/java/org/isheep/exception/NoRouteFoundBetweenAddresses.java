package org.isheep.exception;

import org.isheep.entity.embeddable.Address;

/**
 * Created by raymo on 09/02/2017.
 */
public class NoRouteFoundBetweenAddresses extends RuntimeException {
    private static final long serialVersionUID = -5951986065612263542L;

    public NoRouteFoundBetweenAddresses(final Address recipientAddress) {
        super("No route found to {" + recipientAddress.asStringAddress() + "}, please provide a valid address.");
    }

}
