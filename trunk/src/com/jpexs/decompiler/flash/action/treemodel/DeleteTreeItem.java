/*
 *  Copyright (C) 2010-2013 JPEXS
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jpexs.decompiler.flash.action.treemodel;

import com.jpexs.decompiler.flash.action.Action;
import java.util.List;

public class DeleteTreeItem extends TreeItem {

   public TreeItem object;
   public TreeItem propertyName;

   public DeleteTreeItem(Action instruction, TreeItem object, TreeItem propertyName) {
      super(instruction, PRECEDENCE_PRIMARY);
      this.object = object;
      this.propertyName = propertyName;
   }

   @Override
   public String toString(ConstantPool constants) {
      if (object == null) {
         return hilight("delete ") + propertyName.toString(constants);
      }
      return hilight("delete ") + object.toString(constants) + "." + stripQuotes(propertyName);
   }

   @Override
   public List<com.jpexs.decompiler.flash.action.IgnoredPair> getNeededActions() {
      List<com.jpexs.decompiler.flash.action.IgnoredPair> ret = super.getNeededActions();
      ret.addAll(object.getNeededActions());
      ret.addAll(propertyName.getNeededActions());
      return ret;
   }
}