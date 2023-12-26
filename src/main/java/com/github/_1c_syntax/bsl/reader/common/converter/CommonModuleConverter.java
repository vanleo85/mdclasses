/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * MDClasses is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * MDClasses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with MDClasses.
 */
package com.github._1c_syntax.bsl.reader.common.converter;

import com.github._1c_syntax.bsl.mdo.CommonModule;
import com.github._1c_syntax.bsl.reader.designer.DesignerPaths;
import com.github._1c_syntax.bsl.reader.edt.EDTPaths;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ModuleType;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import java.nio.file.Path;

@CommonConverter
public class CommonModuleConverter extends AbstractReadConverter {

  private static final String URI_FIELD_NAME = "uri";
  private static final String IS_PROTECTED_FIELD_NAME = "isProtected";

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var readerContext = super.read(reader, context);
    Path modulePath;
    if (readerContext.isDesignerFormat()) {
      modulePath = DesignerPaths.modulePath(
        DesignerPaths.moduleFolder(readerContext.getCurrentPath(), MDOType.COMMON_MODULE),
        readerContext.getName(),
        ModuleType.CommonModule
      );
    } else {
      modulePath = EDTPaths.modulePath(
        EDTPaths.moduleFolder(readerContext.getCurrentPath(), MDOType.COMMON_MODULE),
        readerContext.getName(),
        ModuleType.CommonModule
      );
    }
    var protectedModuleInfo = new ProtectedModuleInfo(modulePath);
    readerContext.setValue(URI_FIELD_NAME, protectedModuleInfo.getModulePath().toUri());
    readerContext.setValue(IS_PROTECTED_FIELD_NAME, protectedModuleInfo.isProtected());
    return readerContext.build();
  }

  @Override
  public boolean canConvert(Class type) {
    return CommonModule.class.isAssignableFrom(type);
  }
}
