package services;

import entities.Contract;
import entities.Installment;

import java.time.LocalDate;

public class ContractService {

    private OnlinePaymentService onlinePaymentService;

    public ContractService(OnlinePaymentService onlinePaymentService) {
        this.onlinePaymentService = onlinePaymentService;
    }

    public void processContract(Contract contract, int months){
        Double basicQuota = contract.getTotalValue() / months;
        for (int i = 1; i <= months; i++){
            LocalDate dueDate = contract.getDate().plusMonths(i);

            Double interest = onlinePaymentService.interest(basicQuota, i);
            Double fee = onlinePaymentService.paymentFee(basicQuota + interest);
            Double quota = basicQuota + interest + fee;

            contract.getInstallments().add(new Installment(dueDate, quota));
        }

    }
}
