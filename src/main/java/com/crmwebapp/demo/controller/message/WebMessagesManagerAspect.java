package com.crmwebapp.demo.controller.message;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.crmwebapp.demo.controller.message.Status.Operation;
import com.crmwebapp.demo.entity.Entity;

@Aspect
@Component
class WebMessagesManagerAspect {

	@Pointcut("execution(* com.crmwebapp.demo.controller.*.save(..)) && args(theEntity,theBindingResult,..)")
	private void controllersSave(Entity theEntity, BindingResult theBindingResult) {};

	@Pointcut("execution(* com.crmwebapp.demo.controller.*.delete(..)) && args(theKey,..)")
	private void controllersDelete(Object theKey) {};

	@Pointcut("execution(* com.crmwebapp.demo.controller.*.list*(..)) && args(theModel,..)")
	private void controllersList(Model theModel) {};

	private Status currentStatus = new Status();


	@Before("controllersSave(theEntity, theBindingResult)")
	public void checkAddOrUpdate(Entity theEntity, BindingResult theBindingResult) {


		/* Senza questo controllo verrebbero appesi messaggi tante volte quante si tenta
		 * si aggiungere/modificare un elemento; così invece il messaggio viene assemblato
		 * una sola volta
		 */
		if (!theBindingResult.hasErrors()) {
			currentStatus.setLastOperation(isAnAdd(theEntity) ? Operation.ADD : Operation.UPDATE);
		}
	}

	@AfterReturning("controllersSave(theEntity, theBindingResult)")
	public void setAddOrUpdateStatus(Entity theEntity, BindingResult theBindingResult) {

		String msg = theEntity.getDelineation() + " successfully stored";

		if (!theBindingResult.hasErrors()) {
			currentStatus.setStatus(true);
			currentStatus.appendMsg(msg);
		}
	}

	@AfterReturning("controllersDelete(theKey)")
	public void setDeleteStatus(Object theKey) {

		currentStatus.setSuccessfullDelete(true);
		currentStatus.appendMsg("the selected row has been successfully deleted");
	}

	@AfterReturning("controllersList(theModel)")
	public void addModelAttributeAfterList(Model theModel) {
		Status tempStatus = currentStatus.clone();
		theModel.addAttribute("status", tempStatus);
		currentStatus.reset();
	}

	@AfterReturning("execution(* com.crmwebapp.demo.controller.ProductController.showPurchaseProducts(..)) && args(..,theModel)")
	public void setFilterStatusAndAddModelAttribute(Model theModel) {

		currentStatus.setSuccessfullFilter(true);
		currentStatus.appendMsg("these are the rows you asked for");

		Status tempStatus = currentStatus.clone();
		theModel.addAttribute("status", tempStatus);
		currentStatus.reset();
	}

	private boolean isAnAdd(Entity theEntity) {
		return theEntity.getPrimaryKey() == null;
	}

}
