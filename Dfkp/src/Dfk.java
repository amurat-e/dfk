import java.io.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

public class Dfk 
{

  protected Vector ignoreList = new Vector();  
  
  public Dfk()
  {
  }

    @SuppressWarnings({ "oracle.jdeveloper.java.nested-assignment", "unchecked" })
    public void getIgnoreLines(String filname) throws IOException
{
  String line1 = null;
  int i = 0;
  int comment = 0;
  /* dfk.conf sistem komutlaryla olusturulmus ve isleme tabi tutulacak text 
     girdi dosyasinda aciklama ve goz ardi edilecek aciklama satirlarini icerir
  */
  File dfkconf = new File(filname);
  Reader rdr = new FileReader(dfkconf);
  BufferedReader bfrdrdr = new BufferedReader(rdr);
  /* goz ardi edilecek satirlari ignoreList isimli global yigina ekler 
   */
  while ((line1 = bfrdrdr.readLine()) != null){ 
  
    comment = line1.indexOf("#");
    
    if ((comment < 0)&&(line1.length()>0)) { 
      ignoreList.add(line1);
      //System.out.println(line1+":"+comment+":"+line1.length());
      i++;
    }
  }
}

    @SuppressWarnings("oracle.jdeveloper.java.nested-assignment")
    public void fileOperation (String file_name, String columns) throws IOException
  {
    
      /* Olustrulacak html taglari ile bezenmis html dosyasi girdi dosyasi ile ayni adi
         paylassada tipi html olacaktir
      */
    String new_file = file_name.substring(0,file_name.indexOf("."))+".html";
    File outfile = new File(new_file);
    
    System.out.println(outfile.getAbsoluteFile());
    
    File infile = new File(file_name);
    
    
             
    BufferedReader bfrdrdr = new BufferedReader(new  FileReader(infile));
   
    BufferedWriter bfrwrtr = new BufferedWriter(new  FileWriter(outfile));

    
    int lines = 0;
    /* Okunacak dosyanin (infile) satir sayisi bulunmali...*/
    while (bfrdrdr.readLine() != null) {
    lines++;
    } 

    /* Yazilacak satir sayisi = okunacak dosya satir sayisi - goz ardi edilecek satirlarin sayisi */
    int wlines = lines - ignoreList.size();   

    bfrdrdr.close();
    
    /* okunacak (infile) ve yazilacak (outfile) dosyalari icin satir katarlari tanimi */ 
    String line1 = null;
    String line2 = null;
    String line0 = null;
    /* Okunacak dosyanin (infile) satirlarinin atilacagi karakter yigini*/ 
    char[] ca;
    /* yazilacak dosyanin (outfile) karakter yiginin (cb) okunan dosyanin (infile) karakter yigini + HTML tag leri
     cb = okunan dosyanin buyukugu + satirSayisi(KolonSayisi*KarakterS(</td><td>))+satirSayisi*KarakterS(<tr><td></td></tr>) 
    */
    char[] cb = new char[(int) infile.length() + wlines*(Integer.parseInt(columns)*9)+wlines*20];
    boolean sw = false;
    int ind1 = 0;
    int ind2 = 0;
    int j = 0;
    int k=0;

    System.out.println(",  Satir :"+lines+" - "+ignoreList.size()+", Kolon :"+columns);
    System.out.println("\n");

    Enumeration list = ignoreList.elements();
    
    //System.out.println(">"+ignoreList.size());

    line2="<!DOCTYPE html><html><body><table>";

    bfrdrdr = new BufferedReader(new  FileReader(infile));
    while ((line1 = bfrdrdr.readLine()) != null){
    
     ind1 = 0;
     while (list.hasMoreElements())
      {
        ind2 = line1.indexOf((String)list.nextElement());
        //System.out.println(".:."+ind2);      
        ind1 = ind1 + ind2;
      }
      
      list=ignoreList.elements();
      
      //System.out.println("::"+ind1);  
     
     int okval = -1*(ignoreList.size()-1);
     
     //System.out.println("-*"+okval);
      
      if (ind1 < okval) {
    
        System.out.println(line1);
      
        ca = line1.toCharArray();

        cb[j++]='<';  cb[j++]='t'; cb[j++]='r'; cb[j++]='>'; cb[j++]='<'; cb[j++]='t'; cb[j++]='d'; cb[j++]='>';   

      for (int i=0; i < ca.length; i++)
      {
	j++;
	cb[j]=ca[i];
	k=i;
        if (cb[j] == ' ' )
        {
          if (!sw) {
          sw = true;
          cb[j++]='<';cb[j++]='/';cb[j++]='t';cb[j++]='d';cb[j++]='>';cb[j++]='<';cb[j++]='t';cb[j++]='d';cb[j++]='>';
          }
        } else 
        {
          sw = false; 
        }
      }
	cb[j++]=ca[k];
	cb[j++]='<';cb[j++]='/';cb[j++]='t';cb[j++]='d';cb[j++]='>'; cb[j++]='<'; cb[j++]='/'; cb[j++]='t';cb[j++]='r';cb[j++]='>';

    }

    }
     line0 = new String(cb);
     line2 = line2+line0;
     line2=line2+"</table></body></html>";
     bfrwrtr.write(line2+(char)13+"\n"); 
     bfrwrtr.flush();
     bfrwrtr.close();
     bfrdrdr.close();
  }
  /**
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    Dfk dfk = new Dfk();
    
    
    
    Date dt = new Date();
    
    System.out.println(dt.toString()+" args["+args.length+"]");
    System.out.println("2005,2013 AME(C)");
    
    for (int j=0; j<args.length; j++){ 
    System.out.print(":"+args[j]);
    }
    
   if (args.length < 1) 
    {
      System.out.println("Yanlis Kullanim Bagdat tan Doner...");
      System.out.println("Soyleki : java Dfk filename columns confile");
      System.exit(-1);
    }    
  
    try{
      dfk.getIgnoreLines(args[2]);
      dfk.fileOperation(args[0], args[1]);
    } catch(IOException ioe)
    {
      System.out.println("*** Hata! Hata! hep hata!!! "+ioe.getMessage());
      ioe.printStackTrace();
    }

    System.out.println("\n"+"EOJ");
   
  }
}