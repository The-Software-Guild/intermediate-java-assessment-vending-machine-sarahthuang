
package com.mattu.vendingmachine.service;

import com.sal.vendingmachine.dao.*;
import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.service.*;
import com.sal.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author salajrawi
 */
public class VendingMachineServiceImplTest {
    
    public static VendingMachineService service;
    
    public VendingMachineServiceImplTest() {
        VendingMachineDao dao = new VendingMachineDaoImpl();
        AuditDao auditDao = new AuditDaoImpl();
        service = new VendingMachineServiceImpl(dao, auditDao);
    }
    
    @BeforeAll
    public static void setUpClass() throws VendingMachineException{
        //implement
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws VendingMachineException {
       //implement
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testLoadItemsInStock() throws VendingMachineException, VendingMachinePersistenceException, VendingMachineNoItemInventoryException {
        try {
            System.out.println("loadItemsInStock");
            BigDecimal bd = new BigDecimal("2.50");
            Item i1 = new Item("001", "Coke", bd, 5);

            Map<String, Item> expResult = new TreeMap<>();
            expResult.put("001", i1);

            Map<String, Item> result = service.loadItemsInStock();
            assertEquals(expResult, result, "Test products loaded from the file.");
        } catch (VendingMachineNoItemInventoryException | VendingMachinePersistenceException ex) {
            fail("Product was valid. No exception should have been thrown.");
        }
    }

    @Test
    public void testGetChosenItem() throws VendingMachineNoItemInventoryException {
        System.out.println("getChosenItem");
        BigDecimal bd = new BigDecimal("2.50");

        Item expResult = new Item("001", "Coke", bd, 5);
        Item result = service.getChosenItem("001");

        assertEquals(expResult, result, "Check both items are equal.");
        assertEquals(expResult.getName(), result.getName(), "Check if both names are equals.");
    }

    @Test
    public void testCheckSufficientMoneyToBuyProduct_VendingMachineInsufficientFundsException()
            throws VendingMachineInsufficientFundsException, VendingMachineNoItemInventoryException {
        System.out.println("checkSufficientMoneyToBuyProduct");
        BigDecimal inputAmount = new BigDecimal("0.50");
        Item item = service.getChosenItem("001");
        //TODO
//        Exception exception = (Exception) assertThrows(VendingMachineInsufficientFundsException.class, service.checkSufficientMoneyToBuyProduct(inputAmount, item));
    }
    @Test
    public void testCheckSufficientMoneyToBuyProduct()
            throws VendingMachineInsufficientFundsException, VendingMachineNoItemInventoryException {
        try {
            System.out.println("checkSufficientMoneyToBuyProduct");
            BigDecimal inputAmount = new BigDecimal("3.50");
            Item item = service.getChosenItem("001");
            service.checkSufficientMoneyToBuyProduct(inputAmount, item);
        } catch (VendingMachineInsufficientFundsException | VendingMachinePersistenceException ex) {
            fail("Sufficient funds to buy product. No exception should have been thrown.");
        }
    }

    @Test
    public void testCalculateChange() throws VendingMachineNoItemInventoryException {
        System.out.println("calculateChange");
        BigDecimal amount = new BigDecimal("5");
        Item item = service.getChosenItem("001");
        Change result = service.calculateChange(amount, item);

        assertEquals(10, result.getQuarters(), "Check number of quarters.");
        assertEquals(0, result.getDimes(), "Check number of dimes.");
        assertEquals(0, result.getNickels(), "Check number of nickels.");
        assertEquals(0, result.getPennies(), "Check number of pennies.");
    }

    @Test
    public void testUpdateItemSale() throws Exception {
        System.out.println("updateItemSale");

        Item item = service.getChosenItem("001");
        assertEquals(5, item.getNumInventoryItems(), "Check items in stock.");
        service.updateItemSale(item);
        Item updatedItem = service.getChosenItem("001");
        assertEquals(4, updatedItem.getNumInventoryItems(), "Check items in stock.");
    }
//    /**
//     * Test of getItem method, of class VendingMachineServiceImpl.
//     */
//    @Test
//    public void testGetItem() throws Exception {
//        //implement
//    }
//
//    /**
//     * Test of listAllItems method, of class VendingMachineServiceImpl.
//     */
//    @Test
//    public void testListAllItems() throws Exception {
//        assertEquals(8, service.getAllItems().size(), "8 items");
//    }
//
//    /**
//     * Test of changeInventoryCount method, of class VendingMachineServiceImpl.
//     */
//    @Test
//    public void testChangeInventoryCount(){
//        Item testItem = new Item("Cheetos",  new BigDecimal(2.99).setScale(2,RoundingMode.FLOOR), 18);
//        try{
//            service.changeInventoryCount(testItem, 100);
//            assertNotNull(testItem, "Item should not be null");
//            assertEquals(100, testItem.getNumInventoryItems(), "Inventory item should be 100");
//        }catch(VendingMachinePersistenceException e){
//            fail("No way it will go wrong");
//        }
//
//        try{
//            service.changeInventoryCount(testItem, -100);
//        }catch(VendingMachinePersistenceException e){
//            System.out.println("the value should not be negative");
//        }
//    }
//
//    /**
//     * Test of sellItem method, of class VendingMachineServiceImpl.
//     */
//    @Test
//    public void testSellItem(){
//         //implement
//    }
    
}
