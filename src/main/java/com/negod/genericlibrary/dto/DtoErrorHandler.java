/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.genericlibrary.dto;

import java.util.EnumMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
 */
public class DtoErrorHandler {
    
    static final Logger LOG = Logger.getLogger(DtoErrorHandler.class);
    
    public enum DtoError {
        
        ERROR_MESSAGE, ERROR_CODE;
    }
    
    EnumMap<DtoError, Value> errors = null;
    
    public DtoErrorHandler() {
    }
    
    public DtoErrorHandler(String message, String code) {
        errors = new EnumMap<>(DtoError.class);
        errors.put(DtoError.ERROR_MESSAGE, new Value(message, DtoError.ERROR_MESSAGE));
        errors.put(DtoError.ERROR_CODE, new Value(code, DtoError.ERROR_CODE));
        LOG.info("Sending ErrorMessage through DTO :" + message);
    }
    
    public boolean hasDtoError() {
        if (errors == null) {
            return false;
        } else {
            return true;
        }
    }
    
    public DtoErrorViewer getError() {
        return new DtoErrorViewer(errors);
    }
    
    public class DtoErrorViewer {
        
        private final EnumMap<DtoError, Value> errors;
        
        public DtoErrorViewer(EnumMap<DtoError, Value> errors) {
            this.errors = errors;
        }
        
        public String getMessage() {
            return errors.get(Dto.DtoError.ERROR_MESSAGE).getValue().getString();
        }
        
        public String getErrorCode() {
            return errors.get(Dto.DtoError.ERROR_CODE).getValue().getString();
        }
    }
    
}
