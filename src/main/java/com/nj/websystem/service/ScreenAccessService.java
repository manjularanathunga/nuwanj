package com.nj.websystem.service;

import com.nj.websystem.model.SystemScreen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenAccessService extends JpaRepository<SystemScreen, Long> {


}
