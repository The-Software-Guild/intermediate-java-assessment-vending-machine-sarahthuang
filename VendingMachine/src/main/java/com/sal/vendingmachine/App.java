
package com.sal.vendingmachine;

import com.sal.vendingmachine.controller.VendingMachineController;
import com.sal.vendingmachine.dao.*;
import com.sal.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.sal.vendingmachine.service.VendingMachinePersistenceException;
import com.sal.vendingmachine.service.VendingMachineService;
import com.sal.vendingmachine.service.VendingMachineServiceImpl;
import com.sal.vendingmachine.ui.UserIO;
import com.sal.vendingmachine.ui.UserIOImpl;
import com.sal.vendingmachine.ui.VendingMachineView;

/**
 *
 * @author salajrawi
 */
public class App {
    public static void main(String[] args) throws VendingMachinePersistenceException, VendingMachineInsufficientFundsException {
        // Instantiate the UserIO implementation
        UserIO myIo = new UserIOImpl();

        // Instantiate the View and wire the UserIO implementation into it
        VendingMachineView myView = new VendingMachineView(myIo);

        // Instantiate the DAO
        VendingMachineDao myDao = new VendingMachineDaoImpl();

        // Instantiate the Audit DAO
        AuditDao myAuditDao = new AuditDaoImpl();

        // Instantiate the Service Layer and wire the DAO and Audit DAO into it
        VendingMachineService myService = new VendingMachineServiceImpl(myDao, myAuditDao);

        // Instantiate the Controller and wire the Service Layer into it
        VendingMachineController controller = new VendingMachineController(myView, myService);

        // Kick off the Controller
        controller.run();
    }
}
