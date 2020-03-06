

/**
 * Name: USAir.java
 * Last Modified: 04/30/2018
 * Author: Max Escutia & Patrick Walker
 * Description: A simple flight search system for USAir to help process customer requests 
 *              of purchasing one-way tickets.
 * */

import java.io.*;
import java.util.*;



public class USAir {
  
 public static void main(String [] args) throws FileNotFoundException {
  //Request the user for files to be read.
  Scanner input = new Scanner(System.in);
  System.out.println("Enter the name of the cityFile:");
  String cityName = input.nextLine();
  System.out.println("Enter the name of the flightFile:");
  String flightName = input.nextLine();
  
  //Will loop through logic of code if user wants to search again
  Scanner scan = new Scanner(System.in);
  boolean stop = false;
  while(!stop) {
   System.out.println("\nFrom?");
   String from = input.nextLine();
   
   
   System.out.println("\nTo?");
   String to = input.nextLine();
  
   USAir finalOutput = new USAir(cityName, flightName, from, to);
   
   System.out.println("\nDo you want to search again? (Type y or n)");
   String s = scan.nextLine();
   if(s.equals("y") || s.equals("Y")) {
    stop = false;
    
   } else if (s.equals("n") || s.equals("N")){
    stop = true;
    System.out.println("\nGoodbye... \nThank you for using USAir search system!");
   }
  }
 }
 
 
 private City [] cities;
 public LinkedList<Path> path = new LinkedList<Path>();

 public USAir(String cityName, String flightName, String from, String to)throws FileNotFoundException{
  
  File cityFile = new File(cityName); 
  File flightFile = new File(flightName);
  
  Scanner cityInput = new Scanner(cityFile);
  Scanner cityInput2 = new Scanner(cityFile);
  Scanner flightInput = new Scanner(flightFile);
  
  int counter = 0;
  while(cityInput.hasNext()){
   cityInput.nextLine();
   counter++;
  }
  cities = new City[counter];
  for(int i = 0;cityInput2.hasNext();i++){
   cities[i]=new City(cityInput2.nextLine().trim());

  }
  while(flightInput.hasNext()){
   String line = flightInput.nextLine();
   Scanner flightInformation = new Scanner(line);
   flightInformation.useDelimiter(",");
   String sourceName = flightInformation.next().trim();
   City sourceCity = findCity(sourceName);
   String destName = flightInformation.next().trim();
   City destinationCity = findCity(destName);
   int flightCost = Integer.parseInt(flightInformation.next().trim());
   Flight newFlight = new Flight(sourceCity,destinationCity,flightCost);
   sourceCity.listofFlights.add(newFlight);


  }
  //Will run the algorithm to find the cheapest flight cost path.
   fly(from, to);
  
 }

 private City findCity(String s){
  if(cities.length==0){
   throw new EmptyStackException ();

  }

  for(int i =0;i<cities.length;i++){
   if(s.equals(cities[i].name)){
    return cities[i];
   }

  }
  return null;
 }
 
 //
 public void fly(String sourceCity, String destinationCity){
  if(findCity(sourceCity)==null){
   System.out.print("\nSorry. USAir does not serve "+sourceCity);
   if(findCity(destinationCity)==null){
    System.out.print(" or "+destinationCity+".\n\n");
   }else{
    System.out.print("\n");
   }
  }else if(findCity(destinationCity)==null){
   System.out.println("\nSorry. USAir does not serve "+destinationCity+".\n");
  }else{
   Stack<City> userPlan = isPath(findCity(sourceCity),findCity(destinationCity));
   if(userPlan.isEmpty()){
    System.out.print("\nSorry. USAir does not fly from "+sourceCity+" to "+destinationCity+".\n");
   }else{
    System.out.print("\nRequest is to fly from "+sourceCity+" to "+destinationCity+".\n");
    Stack<City> reverse = new Stack<City>();
    while(!userPlan.isEmpty()){
     reverse.push(userPlan.pop());
    }
    int totalCost = 0;
    while(!reverse.isEmpty() && reverse.size()>1){
     City city1 = reverse.pop();
     City city2 = reverse.peek();
     Flight f = findFlight(city1,city2);
     System.out.println("Flight from "+f.sourceCity.name+" to "+f.destinationCity.name+"   Cost: $"+f.costOfFlights);
     totalCost+=f.costOfFlights;
    }
    System.out.println("Total Cost ..................... $"+totalCost);
    reset();
   }
  }
 }
 
 //This will check if there is a flight that exists between cities 'sourceCity' and 'destinationCity' 
 private static Flight findFlight(City sourceCity, City destinationCity){
  for(int i = 0; i<sourceCity.listofFlights.size();i++){
    if (sourceCity.listofFlights.get(i).destinationCity == destinationCity){
     return sourceCity.listofFlights.get(i);
    }
  }
    return null;
 }
 
 private Stack<City> isPath(City sourceCity, City destinationCity){
  Stack<City> userPlan = new Stack<City>();

  userPlan.add(sourceCity);
  sourceCity.visited = true;
  while(!userPlan.isEmpty() && userPlan.peek() != destinationCity){
   City currentCity = userPlan.peek();
   //make sure that there are no unvisited cities from currentCity remaining
   if(allVisited(currentCity) == -1){
    userPlan.pop();
   }else{
    //the next unvisited city will be currentCity
    City city1 = currentCity.listofFlights.get(allVisited(currentCity)).destinationCity;
    city1.visited = true;
    userPlan.push(city1);
   }
  }
  return userPlan;
 }

 //checking if a city has been visited
 private int allVisited(City curr){
  for(int i = 0; i < curr.listofFlights.size(); i++){
   if(!curr.listofFlights.get(i).destinationCity.visited)
    return i;
  }
  return -1;
 }
 
 
 //reset returns all City.visited booleans to false
 private void reset(){
  for(int i = 0;i < cities.length; i++){
   cities[i].visited = false;
  }
 }


 //This method will return the total cost of the travel from the bottom to the top of the stack
 private int getCost(Stack<City> it){
  int total = 0;
  Stack<City> reverse = new Stack<City>();
  for(int k = 1; k <= it.size(); k++){
   reverse.push(it.get(it.size()-k)); 
  }
  while(!reverse.isEmpty() && reverse.size() > 1){
   City city1 = reverse.pop();
   City city2 = reverse.peek();
   Flight flight1 = findFlight(city1,city2);
   total += flight1.costOfFlights;
  }
  return total;
 }

 
 //This class will find a path and get the cost of that path
 public class Path{
  public Stack<City> itin;
  public int costOfFlights;

  public Path(Stack<City> i){
   itin = i;
   costOfFlights = getCost(itin);

  }
 }

 //This will establish a city object
 public class City{
  public String name;
  public LinkedList<Flight> listofFlights;
  public boolean visited;

  public City(String nameofCity){
   name = nameofCity;
   listofFlights = new LinkedList<Flight>();
   visited = false;
  }
 }
 
 //This will establish a flight object. It will take the 'from' city and 'to' city and get the cost of that specific flight
 public class Flight {
  public City sourceCity;
  public City destinationCity;
  public int costOfFlights;
  public Flight(City source, City destination ,int city1){
   sourceCity = source;
   destinationCity = destination;
   costOfFlights = city1;
  }
 }
}
