package frc.robot.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HoorayConfig {
    
    

    private static Config config;

    public static Config gimmeConfig() {

        if (config == null) {

            File configFile = findConfig();
            config = parseConfig(configFile);
        }

        return config;
    }



    private static Config parseConfig(File configFile) {

        try {

            String json = FileUtils.readFileToString(configFile, Charset.defaultCharset());

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, Config.class);
        } catch (IOException e) {

            e.printStackTrace();
            throw new RuntimeException("Couldn't Read Config File " + configFile, e);
        }
        
    }



    private static File findConfig() {

        if (new File("/home/lvuser/proto").exists()) {

            return new File("/home/lvuser/deploy/protoConfig.json");
        } else if (new File("/home/lvuser/dev").exists()) {

            return new File("/home/lvuser/deploy/devConfig.json");
        } else {

            return new File("/home/lvuser/deploy/compConfig.json");
        }
    }
    
}