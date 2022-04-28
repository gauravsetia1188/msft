public interface state{
	public Product dispense();
	public void insertCoin();
	public void pressButton();
}

public class NoCoinInsertedState implements state{
	VendingMachine vendingMachine;

	public NoCoinInsertedState(VendingMachine vendingMachine){
		this.vendingMachine = vendingMachine;
	}

	public void insertCoin(){
		machine.setAmount();
		machine.setState(machine.getCoinInsertedState());

	}

	public void pressButton(){
		throw new MachineException("No coin inserted");
	}

	public void dispense(){
		throw new MachineException("No coin inserted");
	}

}

public class CoinInsertedState implements state{

	VendingMachine vendingMachine;

	public NoCoinInsertedState(VendingMachine vendingMachine){
		this.vendingMachine = vendingMachine;
	}

	public void insertCoin(){
		throw new MachineException("Coint already inserted");

	}

	public void pressButton(int aisleNumber){
		if(vendingMachine.checkIfProductAvilable(aisleNumber))
			vendingMachine.setState(vendingMachine.getDispenseState());
		else
			throw new MachineException("Product not avilable.")
	}

	public void dispense(){
		throw new MachineException("Button yet to press");
	}
}

public class DispensingState implements state{
	VendingMachine vendingMachine;

	public NoCoinInsertedState(VendingMachine vendingMachine){
		this.vendingMachine = vendingMachine;
	}

	public void insertCoin(){
		throw new MachineException("Please wait while product is dispensing");

	}

	public void pressButton(int aisleNumber){
		throw new MachineException("Please wait while product is dispensing");
	}

	public void dispense(){
		vendingMachine.getProductAt(aisleNumber);
		vendingMachine.setState(vendingMachine.getNoCoinInsertedState());
	}
}

public class Product{
	String name;
	int product_id;
	int price;
}

public class Inventory{
	Map<Product, Integer> productCountMap;
	Map<Integer, Product> aisleProductMap;
	Stack<Integer> avilableAisle;

	addProduct(Product p){
		if(productCountMap.containsKey(p))
			productCountMap.put(p, productCountMap.getOrDefault(p, 0) + 1);
		else{
			if(!avilableAisle.isEmpty())
				int aisleNumber = stack.pop();
			else
                              throw new MachineException("No aisle to add new product");

			aisleProductMap.put(aisleNumber, product);
			productCountMap.put(p, 1);
		}
	}
}

public class VendingMachine{
	NoCoinInsertedState noCoinInsertedState	= new NoCoinInsertedState(this);
	CoinInsertedState coinInsertedState = new CoinInsertedState(this);
	DispensingState dispensingState = new DispensingState(this);
	State machineState = null;
	int loadedAmount = 0;
	Inventory inventory;

	public VendingMachine(){
		machineState = noCoinInsertedState;
		inventory.loadProduct(List<Product> products);
	}

	public void insertCoin(int amount){
		machineState.insertCoin();
	}

	public void pressButton(){
		machineState.pressButton();
		machineState.dispense();
	}

	/*Gette and setter methods for getting and setting the machine states*/



}
