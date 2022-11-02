package com.example.hello.hospital.service;

import com.example.hello.hospital.dto.Hospital;
import com.example.hello.hospital.parser.ReadLineContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class HostpitalService {

    private final ReadLineContext<Hospital> readLineContext;

    @Autowired
    public HostpitalService(ReadLineContext<Hospital> readLineContext) {
        this.readLineContext = readLineContext;
    }

    public List<Hospital> largeInsertDate() throws IOException {
        String filename = "./src/main/resources/hospitalResources/hospital_fulldata9.tsv";

        return readLineContext.readByLine(filename);
    }
}
