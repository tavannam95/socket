package com.a2m.gen.services.common;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.gen.entities.TccoFile;
import com.a2m.gen.repository.common.TccoFileRepository;

@Service
public class TccoFileService {
	@Autowired
	TccoFileRepository fileRepo;
	
	public TccoFile findBySequence(String id) {
		TccoFile file = fileRepo.findByAtchFleSeq(id);
		if (file == null) { 
			return null;
		}
		return file;
	}
	
	public TccoFile saveTccoFile(TccoFile tccoFile) {
		fileRepo.save(tccoFile);
        return tccoFile;
    }
	
	public TccoFile deleteTccoFile(TccoFile tccoFile) {
		fileRepo.delete(tccoFile);
		return tccoFile;
	}
}
