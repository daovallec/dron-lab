package com.dron.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.dron.constants.Constants;
import com.dron.dto.DestinoDTO;
import com.dron.dto.EntregaDTO;
import com.dron.exception.OutRangeException;
import com.dron.utils.Util;

public class RunDron {
	
   
	 public static void main(String[] args) {

		 
	       ExecutorService executor = Executors.newFixedThreadPool(10);

	       Pattern pattern = Pattern.compile("^[a-aA-Ai-iI-Id-dD-D]*$");
	       List<String> camino = new ArrayList<>();
	       List<String> availableDrones = new ArrayList<>();
	       List<String> bussyDrones = new ArrayList<>();
	       List<DestinoDTO> pathCordenadas = new ArrayList<>();
	       
	       Util.initDron(availableDrones);
	       
	       try {
	    	      File myObj = new File("pedidos.txt");
	    	      Scanner myReader = new Scanner(myObj);
	    	      while (myReader.hasNextLine()) {
	    	        String data = myReader.nextLine();
	    	        Matcher matcher = pattern.matcher(data);
	    	        if(matcher.matches()) {
	    	        	camino.add(data);
	    	        }
	    	      }
	    	      myReader.close();
	    	    } catch (FileNotFoundException e) {
	    	      System.out.println("An error occurred.");
	    	      e.printStackTrace();
	    	    }
	       camino.forEach(aaa -> {System.out.println(aaa);});
	       camino.forEach(name -> {
	    	   DestinoDTO dron = new DestinoDTO();
		       dron.setEjeX(0);
		       dron.setEjeX(0);
		       dron.setOrientacion(Constants.NORTH);
		       try {
				Util.calcularCordenada(name, dron);
			} catch (OutRangeException e) {
				System.out.println("Invalid delivery");
				e.printStackTrace();
			}
	    	   pathCordenadas.add(dron);
	       });
	       
	       List<DestinoDTO> destinos = new ArrayList<>();
	       List<EntregaDTO> entregas = new ArrayList<>();
	       
	       pathCordenadas.forEach(camino1 -> {	    	   
	    	   Map<DestinoDTO,Double> destinoTmp = new HashMap<>();
	    	   pathCordenadas.forEach(camino2 -> {
	    		   if(camino1 != camino2) {
	    			   if(!destinos.contains(camino2)) {	    			   
	    				   destinoTmp.put(camino2,Util.calculateDistanceBetweenPoints(camino1.getEjeX(),camino1.getEjeY(), camino2.getEjeX(), camino2.getEjeY()));
	    			   }
	    		   }
		       });
	    	   //Sort Map
	    	   Map<DestinoDTO,Double> sortedDes = destinoTmp.entrySet()
	                   .stream()
	                   .sorted(Map.Entry.comparingByValue())
	                   .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

	    	   //Asign destinos a Dron
	    	   EntregaDTO entrega = new EntregaDTO();
	    	   Boolean added = false;
	    	   if(!sortedDes.isEmpty()) {
	    		   if(availableDrones.isEmpty()) {
	    			   availableDrones.addAll(bussyDrones);
	    			   bussyDrones.clear();
	    		   }
	    		   added = true;
	    		   entrega.setDronName(availableDrones.get(0));
	    		   DestinoDTO e1 = sortedDes.entrySet().iterator().next().getKey();
	    		   sortedDes.remove(sortedDes.entrySet().iterator().next().getKey());
	    		   bussyDrones.add(availableDrones.get(0));
	    		   availableDrones.remove(0);
	    		   entrega.setEntrega1(e1);
	    		   destinos.add(e1);
	    	   }
	    	   if(!sortedDes.isEmpty()) {
	    		   DestinoDTO e2 = sortedDes.entrySet().iterator().next().getKey();
	    		   sortedDes.remove(sortedDes.entrySet().iterator().next().getKey());
	    		   entrega.setEntrega2(e2);
	    		   destinos.add(e2);
	    	   }
	    	   if(!sortedDes.isEmpty()) {
	    		   DestinoDTO e3 = sortedDes.entrySet().iterator().next().getKey();
	    		   sortedDes.remove(sortedDes.entrySet().iterator().next().getKey());
	    		   entrega.setEntrega3(e3);
	    		   destinos.add(e3);
	    	   }
	    	   if(added) {
	    		   entregas.add(entrega); 
	    	   }
	       });

	       
	       entregas.forEach(camp -> {
	    	   System.out.println(camp.getDronName() +": " + camp.getEntrega1().getEjeX() + ", " + camp.getEntrega1().getEjeY());
	    	   if( camp.getEntrega2() != null)
	    		   System.out.println(camp.getDronName() +": " + camp.getEntrega2().getEjeX() + ", " + camp.getEntrega2().getEjeY());
	    	   if( camp.getEntrega3() != null)
	    		   System.out.println(camp.getDronName() +": " + camp.getEntrega3().getEjeX() + ", " + camp.getEntrega3().getEjeY());
	       });
	   }

	 
	}





