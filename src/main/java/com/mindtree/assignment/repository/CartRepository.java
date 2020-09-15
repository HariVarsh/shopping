package com.mindtree.assignment.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mindtree.assignment.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
	
	@Transactional
	@Modifying
	@Query("update Cart c set c.count = ?2 where c.cartId=?1 ")
	void update(int cartId, int count);

	/*
	 * if that product count becomes zero it should be deleted from db
	 */

	@Transactional
	@Modifying
	@Query("delete from Cart c where c.count=?2 and cartId=?1")
	void deleteThatRow(int cartId,int count);

	/*
	 * to delete the entire cart
	 */
	@Transactional
	@Modifying
	@Query("delete from Cart where customerEmail=?1")
	void deleteFullCart(String customerEmail);
}
