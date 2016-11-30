import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Vlad Airinei on 11/30/2016.
 */
public class MainRouterPatchCheck {

    public static void main(String[] args) throws FileNotFoundException {
        //If there is no file provided as a argument
        //The default file is file.csv attached in the zip
        String fileName= "file.csv";

        if(args.length >0){
            fileName = args[0];
        }

        ArrayList<Router> routers = parseCSV(fileName);

        ArrayList<Router> toPatch = patchRouters(routers);

        toPatch.forEach( it ->{
            System.out.println(it.toString());
        });

    }

    public static ArrayList<Router> parseCSV(String fileName)throws FileNotFoundException{
        File inputFile = new File(fileName);
        Scanner scanner = new Scanner(inputFile);
        ArrayList<Router> parsed = new ArrayList<Router>();
        //Reading and parsing the CSV
        int lineNr = 0;
        while(scanner.hasNext()){
            lineNr++;
            String line = scanner.nextLine();
            //Avoiding the first line because is the header
            // And contains the name of the attributes
            if(lineNr != 1){

                //System.out.println(lineNr + " " + line);

                String[] elems = line.split(",");

                String hostname = elems[0];
                String ipAddress = elems[1];
                boolean isPatched = elems[2].toLowerCase().equals("yes") ? true : false;
                String osVersion = elems[3];

                Router temp = new Router(hostname,ipAddress,isPatched,osVersion);

                if(elems.length == 5){
                    String notes = elems[4];
                    temp.setNotes(notes);
                }

                parsed.add(temp);
            }
        }
        return parsed;
    }

    public static ArrayList<Router> patchRouters(ArrayList<Router> routers){
        ArrayList<Router> toPatch = new ArrayList<Router>();
        Map<String,Integer> hostnames = new HashMap<>();
        Map<String,Integer> IPs = new HashMap<>();

        routers.forEach( it -> {
            //Getting the number of routers per hostname
            if(!hostnames.containsKey(it.getHostname().toLowerCase())){
                hostnames.put(it.getHostname().toLowerCase(),1);
            }else{
                hostnames.put(it.getHostname().toLowerCase(),hostnames.get(it.getHostname().toLowerCase())+1);
            }

            //Getting the number of routers per IP
            if(!IPs.containsKey(it.getIP().toLowerCase())){
                IPs.put(it.getIP().toLowerCase(),1);
            }else{
                IPs.put(it.getIP().toLowerCase(),IPs.get(it.getIP().toLowerCase())+1);
            }
        });

        routers.forEach( it->{

            boolean isValidToPatch = checkPatchingValidity(it,hostnames,IPs);

            if(isValidToPatch){
                toPatch.add(it);
            }

        });

        return toPatch;
    }

    public static boolean checkPatchingValidity(Router rout, Map<String,Integer> hostnames, Map<String,Integer> IPs){

        boolean ok = true;

        //The router has not already been patched
        if(rout.isPatched()){
            ok=false;
        }

        //The current version of the router OS is 12 or above
        if(rout.getOSVersion()<12.0){
            ok=false;
        }

        //There are no other routers which share the same IP address
        if(IPs.get(rout.getIP().toLowerCase()) > 1){
            ok=false;
        }

        //There are no other routers which share the same hostname
        if(hostnames.get(rout.getHostname().toLowerCase()) > 1){
            ok=false;
        }

        return ok;
    }


}

