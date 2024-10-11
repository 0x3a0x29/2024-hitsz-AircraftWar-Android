package com.example.aircraftwar2024.data;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public interface ScoreDao{
    public void addData(ScoreData player) throws IOException;
    public void deleteData(int index) throws IOException;
    public List<ScoreData> getAllData();
    public void writeToFile() throws IOException;
    public void sortByScore() throws IOException;
}
