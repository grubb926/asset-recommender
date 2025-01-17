package finance.simply.asset.recommender.service.impl;

import finance.simply.asset.recommender.model.Asset;
import finance.simply.asset.recommender.repository.AssetRepository;
import finance.simply.asset.recommender.service.AssetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssetServiceImplTest {

    @Mock
    private AssetRepository assetRepository;

    private AssetService assetService;

    @BeforeEach
    public void setUp() {
        assetService = new AssetServiceImpl(assetRepository);
    }

    @Test
    public void whenGettingAssetsWithCostUnderMaxCost_andAssetsFound_thenReturnsListOfAssets() {
        double maxCost = 55000;
        when(assetRepository.findByMaxCost(maxCost)).thenReturn(generateExampleAssets());

        final List<Asset> results = assetService.getAssetsWithCostUnder(maxCost);

        assertEquals(3, results.size());
        assertEquals("Tractor", results.get(0).getName());
        assertEquals("Baler", results.get(1).getName());
        assertEquals("Taxi 1", results.get(2).getName());

    }

    @Test
    public void whenGettingAssetsWithCostUnderMaxCost_andAssetsNotFound_thenReturnsEmptyList() {
        double maxCost = 25000;
        when(assetRepository.findByMaxCost(maxCost)).thenReturn(new ArrayList<>());

        final List<Asset> results = assetService.getAssetsWithCostUnder(maxCost);

        assertTrue(results.isEmpty());
    }

    @Test
    public void whenGettingSumOfAllAssetTypes_thenReturnsMapOfAssetTypesAndSumOfEachType() {
        when(assetRepository.findByAssetType(Asset.Type.AGRICULTURE)).thenReturn(generateExampleAgricultureAssets());
        when(assetRepository.findByAssetType(Asset.Type.TRANSPORT)).thenReturn(generateExampleTransportAssets());
        when(assetRepository.findByAssetType(Asset.Type.CONSTRUCTION)).thenReturn(new ArrayList<>());
        when(assetRepository.findByAssetType(Asset.Type.WASTE)).thenReturn(new ArrayList<>());

        final Map<Asset.Type, Double> results = assetService.getSumOfAllTypes();
        assertEquals(4, results.size());
        assertEquals(105000, results.get(Asset.Type.AGRICULTURE));
        assertEquals(50000, results.get(Asset.Type.TRANSPORT));
    }

    private List<Asset> generateExampleAssets() {
        final List<Asset> assets = new ArrayList<>();
        assets.add(new Asset(1, "Tractor", 50000, Asset.Type.AGRICULTURE));
        assets.add(new Asset(2, "Baler", 55000, Asset.Type.AGRICULTURE));
        assets.add(new Asset(3, "Taxi 1", 20000, Asset.Type.TRANSPORT));
        return assets;
    }

    private List<Asset> generateExampleAgricultureAssets() {
        final List<Asset> assets = new ArrayList<>();
        assets.add(new Asset(1, "Tractor", 50000, Asset.Type.AGRICULTURE));
        assets.add(new Asset(2, "Baler", 55000, Asset.Type.AGRICULTURE));
        return assets;
    }

    private List<Asset> generateExampleTransportAssets() {
        final List<Asset> assets = new ArrayList<>();
        assets.add(new Asset(3, "Taxi 1", 20000, Asset.Type.TRANSPORT));
        assets.add(new Asset(4, "Taxi 2", 30000, Asset.Type.TRANSPORT));
        return assets;
    }


}