/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package es.udc.ws.runfic.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThriftInstanceNotFoundException extends TException implements org.apache.thrift.TBase<ThriftInstanceNotFoundException, ThriftInstanceNotFoundException._Fields>, java.io.Serializable, Cloneable, Comparable<ThriftInstanceNotFoundException> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ThriftInstanceNotFoundException");

  private static final org.apache.thrift.protocol.TField INSTANCE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("instanceId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField INSTANCE_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("instanceType", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ThriftInstanceNotFoundExceptionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ThriftInstanceNotFoundExceptionTupleSchemeFactory());
  }

  public String instanceId; // required
  public String instanceType; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    INSTANCE_ID((short)1, "instanceId"),
    INSTANCE_TYPE((short)2, "instanceType");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // INSTANCE_ID
          return INSTANCE_ID;
        case 2: // INSTANCE_TYPE
          return INSTANCE_TYPE;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.INSTANCE_ID, new org.apache.thrift.meta_data.FieldMetaData("instanceId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.INSTANCE_TYPE, new org.apache.thrift.meta_data.FieldMetaData("instanceType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ThriftInstanceNotFoundException.class, metaDataMap);
  }

  public ThriftInstanceNotFoundException() {
  }

  public ThriftInstanceNotFoundException(
    String instanceId,
    String instanceType)
  {
    this();
    this.instanceId = instanceId;
    this.instanceType = instanceType;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ThriftInstanceNotFoundException(ThriftInstanceNotFoundException other) {
    if (other.isSetInstanceId()) {
      this.instanceId = other.instanceId;
    }
    if (other.isSetInstanceType()) {
      this.instanceType = other.instanceType;
    }
  }

  public ThriftInstanceNotFoundException deepCopy() {
    return new ThriftInstanceNotFoundException(this);
  }

  @Override
  public void clear() {
    this.instanceId = null;
    this.instanceType = null;
  }

  public String getInstanceId() {
    return this.instanceId;
  }

  public ThriftInstanceNotFoundException setInstanceId(String instanceId) {
    this.instanceId = instanceId;
    return this;
  }

  public void unsetInstanceId() {
    this.instanceId = null;
  }

  /** Returns true if field instanceId is set (has been assigned a value) and false otherwise */
  public boolean isSetInstanceId() {
    return this.instanceId != null;
  }

  public void setInstanceIdIsSet(boolean value) {
    if (!value) {
      this.instanceId = null;
    }
  }

  public String getInstanceType() {
    return this.instanceType;
  }

  public ThriftInstanceNotFoundException setInstanceType(String instanceType) {
    this.instanceType = instanceType;
    return this;
  }

  public void unsetInstanceType() {
    this.instanceType = null;
  }

  /** Returns true if field instanceType is set (has been assigned a value) and false otherwise */
  public boolean isSetInstanceType() {
    return this.instanceType != null;
  }

  public void setInstanceTypeIsSet(boolean value) {
    if (!value) {
      this.instanceType = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case INSTANCE_ID:
      if (value == null) {
        unsetInstanceId();
      } else {
        setInstanceId((String)value);
      }
      break;

    case INSTANCE_TYPE:
      if (value == null) {
        unsetInstanceType();
      } else {
        setInstanceType((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case INSTANCE_ID:
      return getInstanceId();

    case INSTANCE_TYPE:
      return getInstanceType();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case INSTANCE_ID:
      return isSetInstanceId();
    case INSTANCE_TYPE:
      return isSetInstanceType();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ThriftInstanceNotFoundException)
      return this.equals((ThriftInstanceNotFoundException)that);
    return false;
  }

  public boolean equals(ThriftInstanceNotFoundException that) {
    if (that == null)
      return false;

    boolean this_present_instanceId = true && this.isSetInstanceId();
    boolean that_present_instanceId = true && that.isSetInstanceId();
    if (this_present_instanceId || that_present_instanceId) {
      if (!(this_present_instanceId && that_present_instanceId))
        return false;
      if (!this.instanceId.equals(that.instanceId))
        return false;
    }

    boolean this_present_instanceType = true && this.isSetInstanceType();
    boolean that_present_instanceType = true && that.isSetInstanceType();
    if (this_present_instanceType || that_present_instanceType) {
      if (!(this_present_instanceType && that_present_instanceType))
        return false;
      if (!this.instanceType.equals(that.instanceType))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(ThriftInstanceNotFoundException other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetInstanceId()).compareTo(other.isSetInstanceId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetInstanceId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.instanceId, other.instanceId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetInstanceType()).compareTo(other.isSetInstanceType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetInstanceType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.instanceType, other.instanceType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ThriftInstanceNotFoundException(");
    boolean first = true;

    sb.append("instanceId:");
    if (this.instanceId == null) {
      sb.append("null");
    } else {
      sb.append(this.instanceId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("instanceType:");
    if (this.instanceType == null) {
      sb.append("null");
    } else {
      sb.append(this.instanceType);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ThriftInstanceNotFoundExceptionStandardSchemeFactory implements SchemeFactory {
    public ThriftInstanceNotFoundExceptionStandardScheme getScheme() {
      return new ThriftInstanceNotFoundExceptionStandardScheme();
    }
  }

  private static class ThriftInstanceNotFoundExceptionStandardScheme extends StandardScheme<ThriftInstanceNotFoundException> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ThriftInstanceNotFoundException struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // INSTANCE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.instanceId = iprot.readString();
              struct.setInstanceIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // INSTANCE_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.instanceType = iprot.readString();
              struct.setInstanceTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, ThriftInstanceNotFoundException struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.instanceId != null) {
        oprot.writeFieldBegin(INSTANCE_ID_FIELD_DESC);
        oprot.writeString(struct.instanceId);
        oprot.writeFieldEnd();
      }
      if (struct.instanceType != null) {
        oprot.writeFieldBegin(INSTANCE_TYPE_FIELD_DESC);
        oprot.writeString(struct.instanceType);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ThriftInstanceNotFoundExceptionTupleSchemeFactory implements SchemeFactory {
    public ThriftInstanceNotFoundExceptionTupleScheme getScheme() {
      return new ThriftInstanceNotFoundExceptionTupleScheme();
    }
  }

  private static class ThriftInstanceNotFoundExceptionTupleScheme extends TupleScheme<ThriftInstanceNotFoundException> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ThriftInstanceNotFoundException struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetInstanceId()) {
        optionals.set(0);
      }
      if (struct.isSetInstanceType()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetInstanceId()) {
        oprot.writeString(struct.instanceId);
      }
      if (struct.isSetInstanceType()) {
        oprot.writeString(struct.instanceType);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ThriftInstanceNotFoundException struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.instanceId = iprot.readString();
        struct.setInstanceIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.instanceType = iprot.readString();
        struct.setInstanceTypeIsSet(true);
      }
    }
  }

}

