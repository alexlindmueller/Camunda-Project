package scis.liefart;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class cmsservice implements JavaDelegate {

  private final static Logger LOGGER = Logger.getLogger("cmsservice");

  public void execute(DelegateExecution execution) throws Exception {
	  String thema = (String) execution.getVariable("thema");
      LOGGER.info("funzt1");
      Writer writer = null;

      Date dt = new Date();
	  // 
	  SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );
	  df.setTimeZone( TimeZone.getDefault() );           
	  // 
	  String datum = df.format(dt);  
      
      String template = "<!DOCTYPE html><html lang=\"en\">    <head>        <title>Quittung - {name}</title>        <meta charset=\"utf-8\">        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">    </head>     <body>        <h1>Quittung - {thema} für {name}</h1>        <div>            Datum: {datum}        </div>      <br />  <div>            {name} <br />            {strasze} <br />            {zusatz} <br />            {post} <br />        </div>        <div>            <p>                Das Thema \"{thema}\" wurde mit {prioritaet} bewertet und freigegeben.            </p>            <p>                Dieses Dokument wurde maschinell erstellt und ist ohne Unterschrift gültig.            </p>        </div>    </body>  </html>";
      template = template.replace("{thema}", thema);
      template = template.replace("{name}", (String) execution.getVariable("name"));
      template = template.replace("{datum}", (String) datum);
      template = template.replace("{strasze}", (String) execution.getVariable("strasze"));
      template = template.replace("{zusatz}", (String) execution.getVariable("zusatz"));
      //template = template.replace("{post}", Double.toString((Double) execution.getVariable("post")));
      template = template.replace("{post}", Integer.toString((Integer) execution.getVariable("post")));
      template = template.replace("{prioritaet}", (String) execution.getVariable("prioritaet"));
      
      
      try {
          writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("/home/hu/LieferantenArtikel/"
                		+ (System.currentTimeMillis())
                		+ "-ausdruck.html"
                		), "utf-8"));
          writer.write(template);
      } catch (IOException ex) {
        // report
    	  LOGGER.info("fehler: " + ex.getMessage());  
      } finally {
         try {writer.close();} catch (Exception ex) {LOGGER.info("fehlerspät: " + ex.getMessage());/*ignore*/}
      }
	  //execution.setVariable("thema", thema + thema);
  }

}