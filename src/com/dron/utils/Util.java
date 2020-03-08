package com.dron.utils;

import com.dron.constants.Constants;
import com.dron.dto.DestinoDTO;

public class Util {
	
	private static int maxX = 10;
	private static int maxY = 10;
	
	 public static void calcularCordenada(String camino, DestinoDTO dron) {
		 if(camino.isEmpty()) {
			 return;
		 }
		 switch (dron.getOrientacion()) {
		case Constants.NORTH:
			northOrientation(camino.charAt(0),dron);
			calcularCordenada(camino.substring(1),dron);
			break;
		case Constants.EAST:
			eastOrientation(camino.charAt(0),dron);
			calcularCordenada(camino.substring(1),dron);
			break;
		case Constants.WEST:
			oeastOrientation(camino.charAt(0),dron);
			calcularCordenada(camino.substring(1),dron);
			break;
		case Constants.SOUTH:
			southOrientation(camino.charAt(0),dron);
			calcularCordenada(camino.substring(1),dron);
			break;
		default:
			System.out.println("Lanzar excepcion");
			break;
		}
		 
	 }
	 
	 public static void northOrientation(char actual, DestinoDTO dron) {
		 if(actual == Constants.MOVE_LEFT) {
				dron.setOrientacion(Constants.EAST);
			}else if(actual == Constants.MOVE_RIGHT) {
				dron.setOrientacion(Constants.WEST);
			}else if(actual == Constants.MOVE_ALONG) {
				if(dron.getEjeY() +1 < maxY) {
					dron.setEjeY(dron.getEjeY() +1);
				}else {
					System.out.println("Lanzar excepcion");
				}
					
			}else {
				System.out.println("Lanzar excepcion");
			}
		 
	 }
	 
	 public static void southOrientation(char actual, DestinoDTO dron) {
		 if(actual == Constants.MOVE_LEFT) {
				dron.setOrientacion(Constants.WEST);
			}else if(actual == Constants.MOVE_RIGHT) {
				dron.setOrientacion(Constants.EAST);
			}else if(actual == Constants.MOVE_ALONG) {
				if(dron.getEjeY() -1 > -maxY) {
					dron.setEjeY(dron.getEjeY() -1);
				}else {
					System.out.println("Lanzar excepcion");
				}
			}else {
				System.out.println("Lanzar excepcion");
			}
		 
	 }
	 
	 public static void eastOrientation(char actual, DestinoDTO dron) {
	 if(actual == Constants.MOVE_LEFT) {
			dron.setOrientacion(Constants.SOUTH);
		}else if(actual == Constants.MOVE_RIGHT) {
			dron.setOrientacion(Constants.NORTH);
		}else if(actual == Constants.MOVE_ALONG) {
				dron.setEjeX(dron.getEjeX() -1);
		}else {
			System.out.println("Lanzar excepcion");
		}
	 }
	 
	 public static void oeastOrientation(char actual, DestinoDTO dron) {
	 if(actual == Constants.MOVE_LEFT) {
			dron.setOrientacion(Constants.NORTH);
		}else if(actual == Constants.MOVE_RIGHT) {
			dron.setOrientacion(Constants.SOUTH);
		}else if(actual == Constants.MOVE_ALONG) {
				dron.setEjeX(dron.getEjeX() +1);
		}else {
			System.out.println("Lanzar excepcion");
		}
	 }
	 
	 public static double calculateDistanceBetweenPoints(
			  int x1, 
			  int y1, 
			  int x2, 
			  int y2) {       
			    return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
			}
	 
	 public static void doLongWork(String hola) {
	       System.out.println("Running " + hola);
	       try {
	           Thread.sleep(1000l);
	       } catch (InterruptedException e) {
	           e.printStackTrace();
	       }
	   }


}
