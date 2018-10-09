package com.tk.rpg;

import com.tk.rpg.service.ProcessData;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BootstrapCLR implements CommandLineRunner {

    private final ProcessData processData;

    private static final boolean UPDATE_DB = false;


    @Override
    public void run(String... args) throws Exception {

        if (UPDATE_DB) {
            processData.createDatabaseFromRawData();
        }
        else {
            processData.initializeDatabase();
        }
    }

}
