

import java.io.IOException;
import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.webservicex.airport.Airport;
import net.webservicex.airport.AirportSoap;
import net.webservicex.country.*;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
/**
 * Servlet implementation class servlet
 */
@WebServlet("/servlet")
public class servlet extends HttpServlet {
	private String countryName = "";
	private String cityName = "";
	
	public static String getCharacterDataFromElement(Element e) {
  	    Node child = e.getFirstChild();
  	    if (child instanceof CharacterData) {
  	      CharacterData cd = (CharacterData) child;
  	      return cd.getData();
  	    }
  	    return "";
  	  }
	
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Country countryService = new Country();
    	Airport airportService = new Airport();
    	AirportSoap airportSoap = airportService.getAirportSoap();
		CountrySoap countrySoap = countryService.getCountrySoap();
		String firstResult = "";
		String dialer = "";
		String currency = "";
		String isoCode = "";
		String airportCode = "";
		String cityOrAirportName = "";
		String country = "";
		String countryCode = "";
		String runwayLengthFeet = "";
		String airport = "";

		String responseHtml = "";
		
		//How the application server works? 
		//First, it calls the countryService in order to get the ISO code of the country, taken from the user input.
		//After the extraction of the ISO code, the application get the informations of the airport calling the airportService. 
		
		if (!countryName.equals("")) {
			firstResult = countrySoap.getISOCountryCodeByCountyName(countryName);
			
			try {
				Document doc;
				DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			    InputSource is = new InputSource();
			    is.setCharacterStream(new StringReader(firstResult));
				doc = db.parse(is);
				NodeList nodes = doc.getElementsByTagName("Table");
			    Element element = (Element) nodes.item(0);
			    NodeList title = element.getElementsByTagName("CountryCode");
			    Element line = (Element) title.item(0);
			    isoCode = getCharacterDataFromElement(line);	
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			///////////////////////////////////////////////////////////////////////
			airport = airportSoap.getAirportInformationByISOCountryCode(isoCode);
			//////////////////////////////////////////////////////////////////////
		} 
		else if (!cityName.equals(""))
		{
			airport = airportSoap.getAirportInformationByCityOrAirportName(cityName);
		}
		
		try {
			Document doc;
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		    InputSource is = new InputSource();
		    is.setCharacterStream(new StringReader(airport));
			doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("Table");
		    Element element = (Element) nodes.item(0);
		    NodeList title = element.getElementsByTagName("Country"); //it takes the country String
		    Element line = (Element) title.item(0);
		    country = getCharacterDataFromElement(line);	
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/////////////////////////////////////////////////////////////////////////////
		dialer = countrySoap.getISD(country);
		currency = countrySoap.getCurrencyByCountry(country);
		///////////////////////////////////////////////////////////////////////////
		
		try {
			Document doc;
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		    InputSource is = new InputSource();
		    is.setCharacterStream(new StringReader(dialer));
			doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("Table");
		    Element element = (Element) nodes.item(0);
		    
		    NodeList title = element.getElementsByTagName("code"); //it take the dialer String
		    Element line = (Element) title.item(0);
		    dialer = getCharacterDataFromElement(line);	

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Document doc;
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		    InputSource is = new InputSource();
		    is.setCharacterStream(new StringReader(currency));
			doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("Table");
		    Element element = (Element) nodes.item(0);
		    
		    NodeList title = element.getElementsByTagName("Currency"); //it take the dialer String
		    Element line = (Element) title.item(0);
		    currency = getCharacterDataFromElement(line);	

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		try {
			Document doc;
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		    InputSource is = new InputSource();
		    is.setCharacterStream(new StringReader(airport));
			doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("Table");
		    responseHtml = responseHtml + "<NewDataSet>";

			for(int i = 0; i < nodes.getLength(); i++){
			    Element element = (Element) nodes.item(i);
			    NodeList title = element.getElementsByTagName("CityOrAirportName");
			    Element line = (Element) title.item(0);
			    cityOrAirportName = getCharacterDataFromElement(line);
			    responseHtml = responseHtml + "<Table>";

			    responseHtml = responseHtml + "<CityOrAirportName>" + cityOrAirportName + "</CityOrAirportName>";

			    title = element.getElementsByTagName("AirportCode");
			    line = (Element) title.item(0);
			    airportCode = getCharacterDataFromElement(line);
			    responseHtml = responseHtml + "<AirportCode>" + airportCode + "</AirportCode>";
			    title = element.getElementsByTagName("Country");
			    line = (Element) title.item(0);
			    country = getCharacterDataFromElement(line);
			    responseHtml = responseHtml + "<Country>" + country + "</Country>";

			    title = element.getElementsByTagName("CountryCode");
			    line = (Element) title.item(0);
			    countryCode = getCharacterDataFromElement(line);
			    responseHtml = responseHtml + "<CountryCode>" + countryCode + "</CountryCode>";
		    
			    title = element.getElementsByTagName("RunwayLengthFeet");
			    line = (Element) title.item(0);
			    runwayLengthFeet = getCharacterDataFromElement(line);
			    responseHtml = responseHtml + "<RunwayLengthFeet>" + runwayLengthFeet + "</RunwayLengthFeet>";

			    
			    responseHtml = responseHtml + "<dialer>" + dialer + "</dialer>";
			    responseHtml = responseHtml + "<Currency>" + currency + "</Currency>";
			    responseHtml = responseHtml + "</Table>";
			}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		responseHtml = responseHtml + "</NewDataSet>";
		response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8"); 
        response.getWriter().write(responseHtml);          }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		countryName = request.getParameter("country");
		cityName = request.getParameter("city");
//		System.out.println(city);
//		System.out.println(country);
		response.sendRedirect("http://localhost:8080/task-1/ClientPrintResult.jsp");
		 
	}

}
