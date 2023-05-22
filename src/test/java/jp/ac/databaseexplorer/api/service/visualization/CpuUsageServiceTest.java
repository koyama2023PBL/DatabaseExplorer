package jp.ac.databaseexplorer.api.service.visualization;

import jp.ac.databaseexplorer.api.model.visualization.CpuUsageApiRequest;
import jp.ac.databaseexplorer.api.model.visualization.CpuUsageApiResponse;
import jp.ac.databaseexplorer.api.model.visualization.CpuUsageData;
import jp.ac.databaseexplorer.common.component.csv.impl.SarCpuCsvReader;
import jp.ac.databaseexplorer.common.exception.ApplicationException;
import jp.ac.databaseexplorer.common.exception.SystemException;
import jp.ac.databaseexplorer.storage.visualization.SarCpu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CpuUsageServiceTest {

  @Mock
  private SarCpuCsvReader reader;

  private CpuUsageService service;

  @BeforeEach
  public void setUp() {
    service = new CpuUsageService(reader);
  }

  @Test
  public void testGetCpuUsage() throws ApplicationException, SystemException {
    Date startTime = new Date();
    Date endTime = new Date();

    CpuUsageApiRequest request = new CpuUsageApiRequest(startTime, endTime);

    SarCpu sarCpu = new SarCpu();
    sarCpu.setTimestamp(startTime);
    sarCpu.setIdle(0.2);

    when(reader.read(startTime, endTime)).thenReturn(List.of(sarCpu));

    CpuUsageApiResponse response = service.getCpuUsage(request);

    assertEquals(startTime, response.getStartTime());
    assertEquals(endTime, response.getEndTime());
    CpuUsageData[] data = response.getCpuUsageData();
    assertEquals(1, data.length);
    assertEquals(startTime, data[0].getTimestamp());
    assertEquals(0.8, data[0].getUsage());
  }

  @Test
  public void testGetCpuUsageThrowsApplicationException() throws ApplicationException, SystemException {
    Date startTime = new Date();
    Date endTime = new Date(startTime.getTime() - 1); // endTime is before startTime

    CpuUsageApiRequest request = new CpuUsageApiRequest(startTime, endTime);

    assertThrows(ApplicationException.class, () -> service.getCpuUsage(request));
  }

  @Test
  public void testGetCpuUsageThrowsSystemException() throws ApplicationException, SystemException {
    Date startTime = new Date();
    Date endTime = new Date();

    CpuUsageApiRequest request = new CpuUsageApiRequest(startTime, endTime);

    when(reader.read(startTime, endTime)).thenThrow(SystemException.class);

    assertThrows(ApplicationException.class, () -> service.getCpuUsage(request));
  }
}
