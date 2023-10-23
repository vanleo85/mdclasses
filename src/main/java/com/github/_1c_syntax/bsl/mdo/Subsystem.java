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
package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MdoReference;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
public class Subsystem implements MDObject, ChildrenOwner {

  /*
   * MDObject
   */

  @Default
  String uuid = "";
  @Default
  String name = "";
  @Default
  MdoReference mdoReference = MdoReference.EMPTY;
  @Default
  ObjectBelonging objectBelonging = ObjectBelonging.OWN;
  @Default
  String comment = "";
  @Default
  MultiLanguageString synonym = MultiLanguageString.EMPTY;
  @Default
  SupportVariant supportVariant = SupportVariant.NONE;

  /*
   * Свое
   */

  /**
   * Признак "Включать в командный интерфейс"
   */
  boolean includeInCommandInterface;

  /**
   * Включать в состав справки
   */
  boolean includeHelpInContents;

  /**
   * Объекты, входящие в состав подсистемы
   */
  @Singular("addContent")
  List<MdoReference> content;

  /**
   * Пояснение/Описание
   */
  @Default
  MultiLanguageString explanation = MultiLanguageString.EMPTY;

  /**
   * Родительская подсистема
   */
  @Default
  MdoReference parentSubsystem = MdoReference.EMPTY;

  @Singular("subsystem")
  List<Subsystem> subsystems;

  /*
   * Для ChildrenOwner
   */

  @Override
  @NonNull
  public List<MD> getChildren() {
    return subsystems.stream()
      .map(MD.class::cast)
      .toList();
  }

  /*
   * свое
   */

  /**
   * Возвращает список подсистем, в которые входит указанная ссылка на объект
   *
   * @param mdoRef             - ссылка на искомый объект
   * @param addParentSubsystem - признак необходимости добавлять родительскую (текущую) подсистему в список,
   *                           если объект присутствует в дочерних.
   *                           Используется для кейса: раз есть в дочерней, то считаем что и ко всем родителям
   *                           тоже относится
   * @return Список подсистем, в состав которых включена ссылка
   */
  public List<Subsystem> included(MdoReference mdoRef, boolean addParentSubsystem) {
    List<Subsystem> includedSubsystems = getSubsystems().stream()
      .flatMap(child -> child.included(mdoRef, addParentSubsystem).stream())
      .collect(Collectors.toList());

    if ((!includedSubsystems.isEmpty() && addParentSubsystem) || content.contains(mdoRef)) {
      includedSubsystems.add(this);
    }

    return includedSubsystems;
  }
}
