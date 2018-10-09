package com.tk.rpg.service;

import java.io.IOException;

public interface ProcessData {

    void initializeDatabase() throws IOException;

    void createDatabaseFromRawData() throws IOException;
}
