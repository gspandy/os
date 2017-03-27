package n.web.controller.back;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import n.core.service.support.IGenericService;
import n.entity.ply.Bank;
import n.service.ply.IBankService;
import n.table.dto.ply.BankCreateDTO;
import n.table.dto.ply.BankDTO;
import n.table.dto.ply.BankUpdateDTO;
import n.table.ply.BankTable;
import n.web.controller.support.CRUDController;

@Controller
@RequestMapping("back/" + BankController.PATH)
public class BankController extends CRUDController<Bank, Integer, BankDTO, BankCreateDTO, BankUpdateDTO, BankTable<BankDTO>> {

	public static final String PATH = "admin/bank";

	public BankController() {
		super(PATH);
	}
	
	@Resource
	private IBankService bankService;

	@Override
	protected IGenericService<Bank, Integer> getService() {
		return bankService;
	}

}
