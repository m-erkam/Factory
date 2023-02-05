import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Project1{
    public static void main(String[] args) throws IOException {
        FactoryImpl factoryLine = new FactoryImpl();    // Creating a new factory line
        File inputFile = new File(args[0]);             // Reading file from program arguments
        FileWriter outputFile = new FileWriter(args[1]);
        Scanner readFile;
        try{
            readFile = new Scanner(inputFile);
            while(readFile.hasNextLine()){              // Program takes all inputs until the lines in the file end
                String[] inputs = readFile.nextLine().split(" ");
                switch (inputs[0]){                     // According to inputs, program decides which method to call
                    case "AF":                          // Calls add first method and adds given product
                        factoryLine.addFirst(new Product(Integer.parseInt(inputs[1]),
                                Integer.valueOf(inputs[2])));
                        break;
                    case "AL":                         // Calls add last method and adds given product
                        factoryLine.addLast(new Product(Integer.parseInt(inputs[1]),
                                Integer.valueOf(inputs[2])));
                        break;
                    case "A":                           // Calls add method
                        try{
                            factoryLine.add(Integer.parseInt(inputs[1]),    // Takes index
                                    new Product(Integer.parseInt(inputs[2]),// Takes product id
                                            Integer.valueOf(inputs[3])));   // Takes product value
                        }catch (IndexOutOfBoundsException ex){              // Prints error messsage when needed
                            outputFile.write("Index out of bounds."+"\n");
                        }
                        break;
                    case "RF":                          // Calls remove first method
                        try{
                            outputFile.write(factoryLine.removeFirst().toString()+"\n");
                        } catch(NoSuchElementException ex){     // Prints error messsage when needed
                            outputFile.write("Factory is empty."+"\n");
                        }
                        break;
                    case "RL":                          // Calls remove last method
                        try{
                            outputFile.write(factoryLine.removeLast().toString()+"\n");
                        } catch(NoSuchElementException ex){     // Prints error messsage when needed
                            outputFile.write("Factory is empty."+"\n");
                        }
                        break;
                    case "RI":                          // Calls remove index method
                        try{
                            outputFile.write(factoryLine.removeIndex(Integer.parseInt(inputs[1])).toString()+"\n");
                        }catch (IndexOutOfBoundsException ex){      // Prints error messsage when needed
                            outputFile.write("Index out of bounds."+"\n");
                        }
                        break;
                    case "RP":                          // Calls remove product method
                        try{
                            outputFile.write(factoryLine.removeProduct(Integer.parseInt(inputs[1])).toString()+"\n");
                        }catch (NoSuchElementException ex){     // Prints error messsage when needed
                            outputFile.write("Product not found."+"\n");
                        }
                        break;
                    case "F":                           // Calls find method
                        try{
                            outputFile.write(factoryLine.find(Integer.parseInt(inputs[1])).toString()+"\n");
                        }catch (NoSuchElementException ex){     // Prints error messsage when needed
                            outputFile.write("Product not found."+"\n");
                        }
                        break;
                    case "G":                           // Calls get method
                        try{
                            outputFile.write(factoryLine.get(Integer.parseInt(inputs[1])).toString()+"\n");
                        }catch (IndexOutOfBoundsException ex){      // Prints error messsage when needed
                            outputFile.write("Index out of bounds."+"\n");
                        }
                        break;
                    case "U":                           // Calls update method
                        try{
                            outputFile.write(factoryLine.update(Integer.parseInt(inputs[1]),
                                    Integer.valueOf(inputs[2])).toString()+"\n");
                        }catch (NoSuchElementException ex){     // Prints error messsage when needed
                            outputFile.write("Product not found."+"\n");
                        }
                        break;
                    case "FD":                          // Calls filter duplicates method
                        outputFile.write(factoryLine.filterDuplicates()+"\n");
                        break;
                    case "R":                           // Calls reverse method
                        factoryLine.reverse();
                        outputFile.write(factoryLine.toString());
                        break;
                    case "P":                           // Calls to string method
                        outputFile.write(factoryLine.toString());
                        break;
                }
            }
            outputFile.close();     // Closing files
            readFile.close();
        } catch (FileNotFoundException ex1){
            System.out.println(ex1.getMessage());
        }
    }
}