/**
 * Created by Vlad Airinei on 11/30/2016.
 */
public class Router {
    String hostname;
    String IP;
    boolean isPatched;
    double OSVersion;
    String OSVersionString;
    String Notes;


    public Router() {
    }

    public Router(String hostname, String IP, boolean isPatched, String OSVersion, String notes) {
        this.hostname = hostname;
        this.IP = IP;
        this.isPatched = isPatched;
        this.OSVersionString = OSVersion;
        this.OSVersion = Double.parseDouble(OSVersion);
        this.Notes = notes;
    }

    public Router(String hostname, String IP, boolean isPatched, String OSVersion) {
        this.hostname = hostname;
        this.IP = IP;
        this.isPatched = isPatched;
        this.OSVersionString = OSVersion;
        this.OSVersion = Double.parseDouble(OSVersion);
    }

    public String getOSVersionString() {
        return OSVersionString;
    }

    public void setOSVersionString(String OSVersionString) {
        this.OSVersionString = OSVersionString;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public boolean isPatched() {
        return isPatched;
    }

    public void setPatched(boolean patched) {
        isPatched = patched;
    }

    public double getOSVersion() {
        return OSVersion;
    }

    public void setOSVersion(double OSVersion) {
        this.OSVersion = OSVersion;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    @Override
    public String toString() {
        String notes="";

        if(this.getNotes() != null){
            notes = " [" + this.getNotes() + "]";
        }

        return this.getHostname() + " (" + this.getIP() + "), OS version " + this.getOSVersionString() + notes;
    }
}
