package com.ucm.dasi.catan.resource.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ucm.dasi.catan.resource.IResourceManager;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.ResourceType;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ResourceManagerProviderTest {

  @DisplayName("It must get the resources of a key")
  @Tag("ResourceManagerProvider")
  @Test
  public void itMustGetTheResourcesOfAKey() {
    Map<Integer, IResourceManager> resourcesMap = new TreeMap<Integer, IResourceManager>();
    Integer key = 2;
    Map<ResourceType, Integer> keyResourcesMap = new TreeMap<ResourceType, Integer>();
    keyResourcesMap.put(ResourceType.WOOL, 5);
    IResourceManager keyResources = new ResourceManager(keyResourcesMap);
    resourcesMap.put(key, keyResources);

    ResourceManagerProvider<Integer> resourceProvider =
        new ResourceManagerProvider<Integer>(resourcesMap);

    assertEquals(keyResources, resourceProvider.getResourceManager(key));
  }
}
