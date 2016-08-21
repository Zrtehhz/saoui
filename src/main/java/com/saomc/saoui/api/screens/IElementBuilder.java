package com.saomc.saoui.api.screens;

import com.saomc.saoui.elements.Element;

import java.util.List;

/**
 * This is used to add and remove elements within the SAO UI
 * <p>
 * ElementCore are what's displayed on the ingame menu, and the custom main menu. It's used to display everything from custom categories, or custom buttons.
 * <p>
 * Please use this responsibly
 * <p>
 * Created by Tencao on 29/07/2016.
 */
public interface IElementBuilder {

    /**
     * This adds a new Menu onscreen. Menus are main categories, appearing as the first choices onscreen
     *
     * @param category The category name for the menu (Used by other categories and slots)
     * @param icon     The display icon for the category
     * @param gui      The class of the GUI this belongs to. Two examples are the GuiMainMenu, and IngameMenuGUI
     */
    void addMenu(String category, IIcon icon, GuiSelection gui);

    /**
     * This adds a Slot. Slots are effectively buttons onscreen, which when pressed, fires an ActionPressed event
     * This should be your main method of adding new functions to the menu
     *
     * @param name   The display name of the Slot
     * @param parent The parent category
     * @param icon   The display icon for the category
     * @param gui    The class of the GUI this belongs to. Two examples are the GuiMainMenu, and IngameMenuGUI
     */
    void addSlot(String name, String parent, IIcon icon, GuiSelection gui);

    /**
     * This is to gracefully remove a Menu. You should rarely, if ever have a need to remove this, and advise most people not to
     *
     * @param name The name of the menu to remove
     * @param gui  The class of the GUI this belongs to
     */
    void disableMenu(String name, GuiSelection gui);

    /**
     * This is to gracefully remove a Slot. This can be useful if like the Category, you intend on replacing an already defined slot with your own version
     *
     * @param name   The name of the slot to remove
     * @param parent The parent category
     * @param gui    The class of the GUI this belongs to
     */
    void disableSlot(String name, String parent, GuiSelection gui);

    /**
     * This is to force remove a Menu. You should rarely, if ever have a need to remove this, and advise most people not to
     *
     * @param name The name of the menu to remove
     * @param gui  The class of the GUI this belongs to
     */
    void removeMenu(String name, GuiSelection gui);

    /**
     * This is to force remove a Slot. You should rarely, if ever have a need to remove this, and advise most people not to
     *
     * @param name   The name of the slot to remove
     * @param parent The parent category
     * @param gui    The class of the GUI this belongs to
     */
    void removeSlot(String name, String parent, GuiSelection gui);

    /**
     * Gets the element from the list
     *
     * @param name The name of the element
     * @return Returns the element
     */
    Element getElement(String name);

    /**
     * Gets all elements for a gui
     *
     * @param gui The name of the gui
     * @return Returns the list of elements belonging to the category
     */
    List<Element> getforGui(GuiSelection gui);

    /**
     * Gets all elements for a category
     *
     * @param category The name of the category
     * @return Returns the list of elements belonging to the category
     */
    List<Element> getCategoryContent(String category);

    /**
     * Gets all the menus belonging to a gui
     *
     * @param gui The gui the menus belong to
     * @return Returns all the menus that belong to the gui
     */
    List<Element> getMenus(GuiSelection gui);

    /**
     * Gets all slots belonging to a gui
     *
     * @param gui The gui the slots belong to
     * @return Returns all slots that belong to that gui
     */
    List<Element> getSlots(GuiSelection gui);

    /**
     * Enables all elements that belongs to a category
     *
     * @param category The category to enable elements for
     */
    void enableSubElements(String category);

    /**
     * Disables all elements that belongs to a category
     *
     * @param category The category to disable elements for
     */
    void disableSubElements(String category);
}