package com.laiba.BeginnerProjects.PersonalFileTracker;
import java.io.*;
import java.io.FileOutputStream;
/**
 * FILE I/O CONCEPT: Saving and loading objects to/from files
 * SERIALIZATION: Converting objects to byte stream for storage
 */
public class DataPersistenceService {
    //file where we'll store oour data
    private static final String DATA_FILE = "finance_data.ser";
    /**
     * SAVE DATA: Write FinanceManager object to file
     * TRY-WITH-RESOURCES: Automatically closes resources, prevents memory leaks
     */
    public void saveData(FinanceManager financeManager){
        // Try-with-resources: FileOutputStream and ObjectOutputStream will be automatically closed
        try(FileOutputStream fileOut= new FileOutputStream(DATA_FILE);
        ObjectOutputStream out = new ObjectOutputStream(fileOut)){
            out.writeObject(financeManager);
            System.out.println("Finanace Data saved successfully to"+ DATA_FILE);
        }
        catch (IOException e){
            //exception handling: inform user about error
            System.err.println("Error saving finance data: " +
                    e.getMessage());
            e.printStackTrace();
        }
    }
    //load data: read finance mananger object from file
    //handle cases where no data exists
    public FinanceManager loadData(){
        File file = new File(DATA_FILE);
        if(!file.exists()){
            System.out.println("No existing Dta found");
            return new FinanceManager();
        }
        //try-with0resourses fore input stream
        try(FileInputStream fileIn = new FileInputStream(DATA_FILE);
         ObjectInputStream in = new ObjectInputStream(fileIn)){
            FinanceManager financeManager =(FinanceManager) in.readObject();
            System.out.println("Finance dtaa loaded successfully from "+DATA_FILE);
            return financeManager;
        }
        catch (IOException | ClassNotFoundException e){
            System.err.println("error loading finance data "+ e.getMessage());
            e.printStackTrace();
            return new FinanceManager();
        }
    }
    //check if saved data exists
    public boolean dataexists(){
        return new File(DATA_FILE).exists();
    }
    //delete saved data
    public boolean deleteData(){
        File file = new File(DATA_FILE);
        if(file.exists()){
            return file.delete();
        }
        return false;
    }
}
