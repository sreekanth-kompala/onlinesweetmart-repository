package com.cg.onlinesweetmart.service;

import java.util.List;

import com.cg.onlinesweetmart.entity.SweetOrder;

public interface SweetOrderService {

	/**
	 * Updates an existing sweet order.
	 *
	 * @param sweetOrder The sweet order to be updated.
	 * @return The updated sweet order.
	 */
	SweetOrder updateSweetOrder(SweetOrder sweetOrder);

	/**
	 * Cancels a sweet order by its ID.
	 *
	 * @param sweetOrderId The ID of the sweet order to be canceled.
	 * @return The canceled sweet order.
	 */
	SweetOrder cancelSweetOrder(Long sweetOrderId);

	/**
	 * Retrieves a list of all sweet orders.
	 *
	 * @return A list of all sweet orders.
	 */
	List<SweetOrder> showAllSweetOrders();

	/**
	 * Adds a new sweet order for a user.
	 *
	 * @param userId     The ID of the user placing the order.
	 * @param sweetOrder The sweet order to be added.
	 * @return The saved sweet order.
	 */
	SweetOrder addSweetOrder(Long userId, SweetOrder sweetOrder);

	
}
