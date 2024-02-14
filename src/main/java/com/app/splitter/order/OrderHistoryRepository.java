package com.app.splitter.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    List<OrderHistory> findByOrderId(int order_id);
    //List<OrderHistory> findByPerson_Id(int person_id);

    OrderHistory save(OrderHistory o);
    OrderHistory findByOrderIdAndPersonIdAndItemName(int orderID, int personId, String itemName);

    void deleteByOrderId(int orderID);

    List<Object[]> findByPersonId(int personId);

    @Query("SELECT o.orderId, SUM(o.item_price * o.item_qty) AS personTotal " +
            "FROM OrderHistory o " +
            "WHERE o.personId = :personId " +
            "GROUP BY o.orderId")
    List<Object[]> findPersonTotalByPersonId(@Param("personId") int personId);

    Optional<OrderHistory> findByOrderIdAndItemNameAndPersonId(int orderID, String itemName, int personId);
}
