package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.exception.ComponentNotFoundException;

import java.util.List;

public class ClubsContainer {
    public static final String CLUBS_NOT_FOUND = "There is no club that matches the search criteria.";
    private final List<ClubComponent> clubComponents;

    public ClubsContainer(List<ClubComponent> clubComponents) {
        this.clubComponents = clubComponents;
        checkElements();
    }

    private void checkElements() {
        if (getClubComponents().isEmpty()) {
            throw new ComponentNotFoundException(CLUBS_NOT_FOUND);
        }
    }

    protected List<ClubComponent> getClubComponents() {
        return clubComponents;
    }

    public List<String> getClubComponentTitles() {
        return getClubComponents()
                .stream()
                .map(ClubComponent::getClubTitleLinkText)
                .toList();
    }

    public ClubComponent getClubComponentByTitle(String clubTitle) {
        return getClubComponents().stream()
                .filter(current -> current.getClubTitleLinkText()
                        .equalsIgnoreCase(clubTitle))
                .findFirst()
                .orElseThrow(() -> new ComponentNotFoundException("ClubTitle: ", clubTitle, " not Found."));
    }

    public ClubComponent getClubComponentByPartialTitle(String partialTitle) {
        return getClubComponents()
                .stream()
                .filter(current -> current.getClubTitleLinkText().toLowerCase()
                        .contains(partialTitle.toLowerCase())).findFirst()
                .orElseThrow(() -> new ComponentNotFoundException(
                        String.format("Club partialTitle: %s not Found.", partialTitle)));
    }
}
