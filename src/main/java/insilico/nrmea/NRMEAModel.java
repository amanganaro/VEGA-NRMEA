package insilico.nrmea;

import insilico.core.exception.InitFailureException;
import insilico.core.molecule.InsilicoMolecule;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public abstract class NRMEAModel {
  public static final short UNDEFINED = -1;
  public static final short INACTIVE = 0;
  public static final short AGONIST = 1;
  public static final short ANTAGONIST = 2;
  public static final short A_ANTA = 3;
  
  protected final String FragsSource;
  protected ArrayList<Fragment> FragsList;
  static final String PRIMARY = "primary";
  protected NRMEAFragments PrimaryFragsList;


  public NRMEAModel(String FragsSource) {
    this.FragsSource = FragsSource;
    this.FragsList = null;
    this.PrimaryFragsList = null;
  }
  
  protected void InitFrags() throws Exception {
    URL u = getClass().getResource(this.FragsSource);
    this.FragsList = readFragmentsFromFile(u.openStream());
    this.PrimaryFragsList = new NRMEAFragments(getPrimaryFragments());
    initFragsList();
  }
  
  protected abstract void initFragsList() throws Exception;
  
  protected abstract int calculatePrediction(InsilicoMolecule paramInsilicoMolecule) throws Exception;
  
  public short Predict(InsilicoMolecule mol) throws Exception {
    int retval = 0;
    if (this.FragsList == null)
      InitFrags(); 
    retval = calculatePrediction(mol);
    short pred = -1;
    switch (retval) {
      case 1:
        pred = 1;
        return pred;
      case 2:
        pred = 2;
        return pred;
      case 3:
        pred = 3;
        return pred;
    } 
    pred = 0;
    return pred;
  }
  
  protected ArrayList<Fragment> getPrimaryFragments() throws Exception {
    ArrayList<Fragment> primaryFragsList = new ArrayList<>();
    for (Fragment frag : this.FragsList) {
      String st = frag.getType();
      if (st.equalsIgnoreCase("primary"))
        primaryFragsList.add(frag); 
    } 
    if (primaryFragsList.isEmpty())
      throw new Exception("Primary fragment list is empty"); 
    return primaryFragsList;
  }
  
  protected ArrayList<Fragment> getFragmentListOSubType(String subtype) throws Exception {
    ArrayList<Fragment> fragsList = new ArrayList<>();
    for (Fragment frag : this.FragsList) {
      String st = frag.getSubtype();
      if (st.equalsIgnoreCase(subtype))
        fragsList.add(frag); 
    } 
    if (fragsList.isEmpty())
      throw new Exception("Fragment list of type " + subtype + " is empty"); 
    return fragsList;
  }
  
  private static ArrayList<Fragment> readFragmentsFromFile(InputStream source) throws InitFailureException, IOException {
    ArrayList<Fragment> FragsList = new ArrayList<>();
    DataInputStream in = new DataInputStream(source);
    BufferedReader br = new BufferedReader(new InputStreamReader(in));
    String line = br.readLine();
    while (line != null) {
      String[] data = line.split("\t");
      Fragment frag = createFragment(data);
      if (frag != null)
        FragsList.add(frag); 
      line = br.readLine();
    } 
    br.close();
    if (FragsList.isEmpty())
      throw new InitFailureException("Fragments list is empty"); 
    return FragsList;
  }
  
  private static Fragment createFragment(String[] data) throws InitFailureException {
    String id = data[0];
    String frag = data[1];
    String type = data[2];
    String subtype = data[3];
    return new Fragment(id, frag, type, subtype);
  }
}
