package model.dao;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Ikastetxeak;

public class IkastetxeakDao {
	
	public List<Ikastetxeak> getIkastetxeak(){
		
		try {
		// Create ObjectMapper to work with JSON
		ObjectMapper objectMapper = new ObjectMapper();
		
		// Get the file from resources
		InputStream is = getClass().getClassLoader().getResourceAsStream("ikastetxeak.json");
		
		// Create type reference for list of Ikastetxeak
		TypeReference<List<Ikastetxeak>> tr = new TypeReference<List<Ikastetxeak>>() {};
		
		// Read the file and convert it to a list of Ikastetxeak
		List<Ikastetxeak> ikastetxeak = objectMapper.readValue(is, tr);
		
		// return the list of Ikastetxeak
		return ikastetxeak;
		
		}catch(Exception e) {
			System.out.println("Error reading file");
            e.printStackTrace();
            return null;
        }
		
	}

}
