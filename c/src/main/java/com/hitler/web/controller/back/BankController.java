package com.hitler.web.controller.back;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hitler.core.service.support.IGenericService;
import com.hitler.entity.ply.Bank;
import com.hitler.service.ply.IBankService;
import com.hitler.table.dto.ply.BankCreateDTO;
import com.hitler.table.dto.ply.BankDTO;
import com.hitler.table.dto.ply.BankUpdateDTO;
import com.hitler.table.ply.BankTable;
import com.hitler.web.controller.support.CRUDController;

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
