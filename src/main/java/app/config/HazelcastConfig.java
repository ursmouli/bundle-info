package app.config;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;
import org.apache.commons.configuration.tree.DefaultExpressionEngine;

import java.io.File;

public class HazelcastConfig {
    public static void main(String[] args) throws Exception {

        DefaultExpressionEngine engine = new DefaultExpressionEngine();

        // Use a slash as property delimiter
        engine.setPropertyDelimiter("/");
        // Indices should be provided in curly brackets
        engine.setIndexStart("{");
        engine.setIndexEnd("}");
        // For attributes use simply a @
        engine.setAttributeStart("@");
        engine.setAttributeEnd(null);
        // A Backslash is used for escaping property delimiters
        engine.setEscapedDelimiter("\\/");

        // Now install this engine as the global engine
        HierarchicalConfiguration.setDefaultExpressionEngine(engine);

        File hazelcastXml = new File(HazelcastConfig.class.getClassLoader().getResource("hazelcast-client.xml").getFile());
        XMLConfiguration xmlConfiguration = new XMLConfiguration();

        xmlConfiguration.load(hazelcastXml);

        System.out.println(xmlConfiguration.getProperty("group/name"));
        System.out.println(xmlConfiguration.getProperty("group/password"));
        System.out.println(xmlConfiguration.getProperty("network/cluster-members/address"));

        HierarchicalConfiguration.Node root = xmlConfiguration.getRoot();

        for (ConfigurationNode node : root.getAttributes()) {
            //System.out.println(node.getName() + " - " + node.getValue());
        }

        //System.out.println("------------------");
        root.addAttribute(new HierarchicalConfiguration.Node("xmlnstest", "http://www.hazelcast.com/schema/config/test"));

        root = xmlConfiguration.getRoot();
        for (ConfigurationNode node : root.getAttributes()) {
            //System.out.println(node.getName() + " - " + node.getValue());
        }


    }
}
