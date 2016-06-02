package DAO;

import java.util.List;

import guitar.Guitar;
import guitar.GuitarSpec;

public interface IGuitarDao {
	public abstract void addGuitar(String serialNumber, String builder, String model, int numStrings,String type, String backWood,
			String topWood, String price);
	
	public abstract List<Guitar> getGuitars();

	public abstract List<Guitar> searchGuitar(GuitarSpec searchGuitar);
}
