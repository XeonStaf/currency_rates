package org.example.API;

import org.example.DTO.CurrencyInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.nio.charset.Charset;

public class Parser {

    public static CurrencyInfo findCurrencyIdByName(String xml, String currencyCode) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(xml));
            inputSource.setEncoding("windows-1251");

            Document document = builder.parse(inputSource);
            document.getDocumentElement().normalize();

            NodeList currencyList = document.getElementsByTagName("Valute");

            for (int i = 0; i < currencyList.getLength(); i++) {
                Node node = currencyList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String code = element.getElementsByTagName("CharCode").item(0).getTextContent();

                    if (code.equals(currencyCode)) {
                        String cost = element.getElementsByTagName("Value").item(0).getTextContent();
                        String formattedStr = cost.replace(",", ".");

                        return CurrencyInfo.builder()
                                .charCode(code)
                                .name(element.getElementsByTagName("Name").item(0).getTextContent())
                                .id(element.getAttribute("ID"))
                                .value(Double.parseDouble(formattedStr))
                                .numCode(element.getElementsByTagName("NumCode").item(0).getTextContent())
                                .nominal(element.getElementsByTagName("Nominal").item(0).getTextContent())
                                .build();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
